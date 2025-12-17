package ru.softmachine.odyssey.backend.cms.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.LazyInitializer;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ru.softmachine.odyssey.backend.cms.config.ConverterConfig;
import ru.softmachine.odyssey.backend.cms.converter.base.BaseVersionConverter;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.converter.context.EntityMappingContext;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseExternalRef;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseStringDto;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityPatchDto;
import ru.softmachine.odyssey.backend.cms.entity.Entity;
import ru.softmachine.odyssey.backend.cms.entity.FieldValue;
import ru.softmachine.odyssey.backend.cms.dto.base.FieldType;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;
import ru.softmachine.odyssey.backend.cms.dto.field.FieldDefType;
import ru.softmachine.odyssey.backend.cms.evaluation.EvaluationProcessor;
import ru.softmachine.odyssey.backend.cms.exception.EntityNotFoundException;
import ru.softmachine.odyssey.backend.cms.exception.OptimisticLockProtectionException;
import ru.softmachine.odyssey.backend.cms.repository.FieldDefRepository;
import ru.softmachine.odyssey.backend.cms.service.DictionaryService;
import ru.softmachine.odyssey.backend.cms.service.ValueExternalService;
import ru.softmachine.odyssey.backend.cms.utils.DateTimeUtils;
import ru.softmachine.odyssey.backend.cms.utils.EntityDefUtils;
import ru.softmachine.odyssey.backend.model.UprCmsSlugBaseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.softmachine.odyssey.backend.cms.converter.FieldValueUtilsConverter.FIELD_VALUE_COPY;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
                FieldValueConverter.class,
                FieldValueUtilsConverter.class,
        }
)
@Slf4j
public abstract class EntityConverter implements BaseVersionConverter<Entity, EntityDto> {

    public static final String BASE_ENTITY_REF_MAPPER = "BASE_ENTITY_REF";
    public static final String ENTITY_COPY = "ENTITY_COPY";

    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private EntityDefUtils entityDefUtils;
    @Autowired
    private List<EvaluationProcessor<?>> processors;
    @Autowired
    private DictionaryExternalValueConverter dictionaryExternalValueConverter;
    @Autowired
    private CommonBaseRefConverter commonBaseRefConverter;
    @Autowired
    private FieldValueConverter fieldValueConverter;
    @Autowired
    private FieldGeometryValueConverter fieldGeometryValueConverter;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FieldDefRepository fieldDefRepository;
    @Autowired
    private DateTimeUtils dateTimeUtils;
    @Autowired
    private ValueExternalService valueExternalService;

    @Value("${app.mapper.empty-as-null}")
    private Boolean emptyAsNull;

    private static final List<FieldType> GENERATED_TYPES = List.of(
            FieldType.EXPRESSION,
            FieldType.HTML_TEMPLATE,
            FieldType.COLLECTION_FILTERED,
            FieldType.EXTERNAL_VALUE
    );

    /**
     * Служебные и вычисленные поля не перезаписываются
     * при конвертации из DTO
     *
     * @param dto
     * @return
     */
    @Override
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "authorEmail", ignore = true)
    public abstract Entity convertToModel(EntityDto dto);

    /**
     * Сейчас только для корневых entity, для включения в иерархии достаточно добавить @BeforeMapping
     *
     * @param entities
     */
    private void prepareBatchExternalValue(List<Entity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }

        entityDefUtils.getAllFieldDefs(entities.getFirst().getEntityDef().getContainer())
                .filter(fieldDef -> fieldDef.getType() == FieldType.EXTERNAL_VALUE
                        && fieldDef.getExternalConnectionBatch() != null)
                .forEach(fieldDef -> {
                    // кеширующий пакетный запрос внешних значений
                    valueExternalService.getValuesBatchCache(
                            entities.stream().map(Entity::getId).map(Objects::toString).toList(),
                            fieldDef.getId(),
                            fieldDef.getExternalConnectionBatch()
                    );
                });
    }

    /**
     * Маппинг коллекций предполагает оптимизационные мероприятия поэтому выполнен отдельно
     *
     * @param entities
     * @param context
     * @return
     */
    public List<EntityDto> convertToDtosWithContext(List<Entity> entities, @Context EntityMappingContext context) {
        prepareBatchExternalValue(entities);
        return entities.stream().map(entity -> convertToDtoWithContext(entity, context)).toList();
    }

    public abstract EntityDto convertToDtoWithContext(Entity entity, @Context EntityMappingContext context);

    public abstract UprCmsSlugBaseEntity convertToBaseSlugEntity(Entity entity);

    /**
     * Обновляет только переданные values сущности. Если значение ранее
     * не было представлено в сущности, оно будет создано заново.
     *
     * @param entityDto
     * @return возвращает сущность с пропатченными значениями
     */
    public Entity patchEntityValuesWithDto(EntityPatchDto entityDto) {
        var existingEntity = baseEntityMapperByRef(entityDto);
        var updatedAndNewValues = entityDto.getValues().stream().map(value -> {
            var valueModel = fieldValueConverter.convertToModel(value);
            valueModel.setEntity(existingEntity);
            return valueModel;
        });
        // измененные сущности уже изменены6 осталось добавить новые
        existingEntity.getValues().addAll(
                updatedAndNewValues
                        .filter(value -> value.getId() == null)
                        .toList());
        return existingEntity;
    }

    @AfterMapping
    protected void mapEntityDefFields(EntityDto source, @MappingTarget Entity entity) {
        // фильтруем расчетные значения (toList может вернуть immutable collection)
        entity.setValues(new ArrayList<>(entity.getValues().stream()
                .filter(value -> !GENERATED_TYPES.contains(value.getFieldDef().getType())).toList()));
        entity.getValues().forEach(value -> value.setEntity(entity));
    }

    @AfterMapping
    protected void mapEntityToBaseRef(Entity source, @MappingTarget BaseRef dto) {
        dto.setName(source.getValues().stream()
                .filter(value -> value.getFieldDef().isVisibleShort())
                .map(FieldValue::getTextValue)
                .findFirst()
                .orElse(null));
    }

    @Mapping(target = "createdTs", ignore = true)
    @Mapping(target = "updatedTs", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Named(BASE_ENTITY_REF_MAPPER)
    public abstract Entity baseEntityMapperByRef(BaseStringDto ref);

    protected List<FieldValueDto> fieldValueListToFieldValueDtoList1(
            List<FieldValue> values,
            @Context EntityMappingContext context
    ) {
        if (values == null) {
            return null;
        }
        return values.stream()
                .filter(value -> context.isFullView() || value.getFieldDef().isVisibleListView())
                .map(fieldValueConverter::convertToDto)
                .toList();
    }

    /**
     * Метод проверяет совпадение версий бекенда и фронтенда
     * при изменнии сущности (пользовательская оптимистическая блокировка).
     * <p>
     * Инкремент выполняет hibernate
     *
     * @param entityDto
     * @param entity
     */
    @AfterMapping
    protected void checkVersion(
            EntityDto entityDto,
            @MappingTarget Entity entity
    ) {
        if (entityDto.getId() != null
                && entity.getId() != null
                && !Objects.equals(entityDto.getVersion(), entity.getVersion())) {
            throw new OptimisticLockProtectionException("Сущность была изменена", entity.getId().toString(), entity.getVersion());
        }
    }


    /**
     * Метод расширяет entityDto вычислимыми полями на основе EntityDef
     *
     * @param entityDto
     * @param context
     * @param entityDef
     */
    protected List<FieldValueDto> wideEntityByCalculatedProperties(
            EntityDto entityDto,
            LazyInitializer<Map<String, Object>> context,
            EntityDef entityDef
    ) throws ConcurrentException {
        var siblingContext = new HashMap<>(context.get());
        entityDto.setValues(
                ListUtils.union(
                        entityDto.getValues(),
                        entityDefUtils.getAllFieldDefs(entityDef.getContainer())
                                .map(fieldDef -> {
                                    try {
                                        var processor = processors.stream()
                                                .filter(ep -> ep.getFieldType() == fieldDef.getType())
                                                .findFirst();
                                        if (processor.isPresent()) {
                                            var calculatedValue = processor.get().evaluate(
                                                    fieldDef.getExpression(), siblingContext, fieldDef);
                                            if (calculatedValue != null) {
                                                // сохраним в конетксте новую расчитанную переменную
                                                siblingContext.put(fieldDef.getCode(), calculatedValue);

                                                var fieldValue = new FieldValueDto()
                                                        .setFieldDef(commonBaseRefConverter.convertToBaseFieldDef(fieldDef));
                                                if (fieldDef.getType() == FieldType.EXPRESSION
                                                        || fieldDef.getType() == FieldType.EXTERNAL_VALUE
                                                        || fieldDef.getType() == FieldType.HTML_TEMPLATE) {
                                                    fieldValue.setTextValue(calculatedValue.toString());
                                                }
                                                if (fieldDef.getType() == FieldType.COLLECTION_FILTERED) {
                                                    try {
                                                        var sourceCollection = entityDto.getValues().stream()
                                                                .filter(value -> Objects.equals(value.getFieldDef().getCode(),
                                                                        fieldDef.getRefCollectionFieldCode()))
                                                                .findFirst();
                                                        if (sourceCollection.isEmpty()) {
                                                            throw new EntityNotFoundException("Source collection with code: {} was not found",
                                                                    fieldDef.getRefCollectionFieldCode());
                                                        }
                                                        // результат вычисления коллекция сущностей в виде карты значений, необходимо привести
                                                        // к виду служебной DTO отфильтровав исходную коллекцию
                                                        var entityIds = ((Collection<Map<String, Object>>) calculatedValue).stream()
                                                                .map(entityMap -> entityMap.get("id").toString())
                                                                .toList();
                                                        fieldValue.setEntities(
                                                                sourceCollection.get().getEntities().stream()
                                                                        .filter(e -> entityIds.contains(e.getId().toString()))
                                                                        .toList()
                                                        );
                                                    } catch (Exception e) {
                                                        log.warn("Error while calculate COLLECTION FILTER property: {}",
                                                                fieldDef.getCode());
                                                    }
                                                }
                                                return fieldValue;
                                            }
                                        }
                                    } catch (Exception e) {
                                        log.error("Не рассчитать вычислить поле", e);
                                    }
                                    return null;
                                })
                                .filter(Objects::nonNull)
                                .toList()));

        // возвращаем сущность с рассчитанными параметрами
        return entityDto.getValues();
    }

    /**
     * Выполняет расчет EXPRESSION полей (предполагается, что расчет зависит от момента вызова сущности, поэтому
     * выполняется не заранее, а в момент запроса)
     *
     * @param entity    сущность, для поля которой требуется произвести расчет
     * @param entityDto DTO, которая обогащается произведенными расчетами
     */
    @AfterMapping
    protected void evaluateExpressions(
            Entity entity,
            @MappingTarget EntityDto entityDto,
            @Context EntityMappingContext entityMappingContext
    ) {
        // полей для которых необходим контекст может и не быть
        var context = new LazyInitializer<Map<String, Object>>() {

            @Override
            protected Map<String, Object> initialize() {
                return makeFieldMap(entity, true, entityMappingContext.isFullView());
            }
        };
        try {
            wideEntityByCalculatedProperties(entityDto, context, entity.getEntityDef());
        } catch (Exception e) {
            log.warn("Ошибка выполнения вычислений для расчетных полей", e);
        }
    }

    private <T> T objToDictionary(Object value, FieldDef fieldDef, Function<Entity, T> converter) {
        if (fieldDef.getCollectionRef() == null) {
            log.warn("Could not find dictionary class for: {}", fieldDef.getCode());
            return null;
        }
        // ключевое поле
        var fieldEnumSerialized = entityDefUtils.getAllFieldDefs(
                        fieldDef.getCollectionRef().getContainer())
                .filter(fieldDefInner -> Boolean.TRUE.equals(fieldDefInner.getSerializeEnum()))
                .findFirst();

        T dictionaryValue = null;
        if (value instanceof String) {
            // случай серилизации значения справочника в ключевое значение (enum)
            if (fieldEnumSerialized.isPresent()) {
                dictionaryValue = dictionaryService.findDictionaryValueBySerializedEnum(
                        fieldDef.getCollectionRef().getId(),
                        fieldEnumSerialized.get().getId(),
                        value.toString(),
                        converter
                );
            }
        } else {
            if (value instanceof Map) {
                // если перед нами объект, то ищем в справочнике по ключевому полю (возможно потребуется новое)
                if (fieldEnumSerialized.isPresent()) {
                    if (fieldEnumSerialized.get().getFieldDefType() == null
                            || fieldEnumSerialized.get().getFieldDefType() == FieldDefType.FLEX) {
                        dictionaryValue = dictionaryService.findDictionaryValueBySerializedEnum(
                                fieldDef.getCollectionRef().getId(),
                                fieldEnumSerialized.get().getId(),
                                ((Map<?, ?>) value).get(fieldEnumSerialized.get().getCode()).toString(),
                                converter
                        );
                    } else {
                        // поддерживается slug и id
                        Object key = ((Map<?, ?>) value).get(fieldEnumSerialized.get().getCode());
                        if (fieldEnumSerialized.get().getCode().equals("id")) {
                            key = UUID.fromString(key.toString());
                        }
                        dictionaryValue = dictionaryService.findDictionaryValueByFixedField(
                                fieldDef.getCollectionRef().getId(),
                                fieldEnumSerialized.get().getCode(),
                                key,
                                converter
                        );
                    }
                }
            }
        }

        // встроенное создание элемента справочника если разрешено
        if (dictionaryValue == null
                && fieldDef.getCollectionRef().isAllowInlineCreation()
                && value instanceof Map) {
            var newDictionaryEntity = deserializeEntityByMap(
                    fieldDef.getCollectionRef(),
                    (Map<String, Object>) value,
                    null
            );
            dictionaryValue = dictionaryService.createDictionaryElement(newDictionaryEntity, converter);
        }

        return dictionaryValue;
    }

    /**
     * Десерилизация даты
     *
     * @param value
     * @return
     */
    protected LocalDate deserializeDate(Object value) {
        if (value instanceof LocalDate) {
            return (LocalDate) value;
        }
        if (value instanceof String) {
            return dateTimeUtils.getLocalDateByString(value.toString());
        }
        return null;
    }

    /**
     * Десерилизация даты
     *
     * @param value
     * @return
     */
    protected ZonedDateTime deserializeDateTime(Object value) {
        if (value instanceof ZonedDateTime) {
            return (ZonedDateTime) value;
        }
        if (value instanceof String) {
            return dateTimeUtils.getZonedDateTimeByString(value.toString());
        }
        return null;
    }

    /**
     * Обработка фиксированных полей
     * Сейчас поддерживается обработка только id
     *
     * @param entityDto
     * @param fieldDef
     * @param sourceMap
     */
    protected void deserializeFixedField(EntityDto entityDto, FieldDef fieldDef, Map<String, Object> sourceMap) {
        if (Objects.equals(fieldDef.getCode(), "id")) {
            entityDto.setId(Optional.ofNullable(sourceMap.get(fieldDef.getCode()))
                    .map(Object::toString)
                    .orElse(null)
            );
        }
    }

    /**
     * Восстановление служебного значения для поля из прикладного
     *
     * @param fieldDef
     * @param sourceMap
     * @return
     */
    protected FieldValueDto deserializeFlexField(FieldDef fieldDef,
                                                 Map<String, Object> sourceMap,
                                                 Map<String, FieldValueDto> currentValueContext,
                                                 BiFunction<UUID, String, EntityDto> entityResolver
    ) {
        if (sourceMap.containsKey(fieldDef.getCode()) && !GENERATED_TYPES.contains(fieldDef.getType())) {
            // берем существующее значение или создаем новое
            FieldValueDto fieldValueDto = currentValueContext.getOrDefault(fieldDef.getCode(), new FieldValueDto());
            fieldValueDto.setFieldDef(commonBaseRefConverter.convertToBaseFieldDef(fieldDef));
            var value = sourceMap.get(fieldDef.getCode());
            switch (fieldDef.getType()) {
                case TEXT, STRING, HTML, COLOR, SLUG, LINK, URL, IMAGE, VIDEO -> {
                    // FIXME: возможно стоит перестать анализировать флаг
                    if (fieldDef.isMultiple() && value instanceof List<?>) {
                        fieldValueDto.setArrayText(((List<?>) value).stream()
                                .map(Object::toString).toList());
                    } else {
                        fieldValueDto.setTextValue(value.toString());
                    }
                }
                case TAGS -> {
                    if (value instanceof Collection<?>) {
                        fieldValueDto.setArrayText(
                                ((Collection<?>) value).stream()
                                        .map(Object::toString).toList());
                    }
                }
                case JSON -> fieldValueDto.setTextValue(value.toString());
                case BOOLEAN -> fieldValueDto.setBooleanValue((Boolean) value);
                case INT -> {
                    if (value instanceof Integer) {
                        fieldValueDto.setIntValue(((Integer) value).longValue());
                    }
                    if (value instanceof Long) {
                        fieldValueDto.setIntValue((Long) value);
                    }
                }
                case DATE -> {
                    if (fieldDef.isMultiple() && value instanceof List<?>) {
                        fieldValueDto.setArrayDate(((List<?>) value).stream()
                                .map(this::deserializeDate).filter(Objects::nonNull)
                                .toList());
                    } else {
                        fieldValueDto.setDateValue(deserializeDate(value));
                    }
                }
                case TIME -> {
                    if (value instanceof LocalTime) {
                        fieldValueDto.setTimeValue((LocalTime) value);
                    }
                    if (value instanceof String) {
                        fieldValueDto.setTimeValue(dateTimeUtils.getLocalTimeByString(value.toString()));
                    }
                }
                case DATETIME -> {
                    if (fieldDef.isMultiple() && value instanceof List<?>) {
                        fieldValueDto.setArrayDateTime(((List<?>) value).stream()
                                .map(this::deserializeDateTime).filter(Objects::nonNull)
                                .toList());
                    } else {
                        fieldValueDto.setDatetimeValue(deserializeDateTime(value));
                    }
                }
                case DOUBLE -> {
                    if (value instanceof Double) {
                        fieldValueDto.setDoubleValue((Double) value);
                    }
                    if (value instanceof Float) {
                        fieldValueDto.setDoubleValue(((Float) value).doubleValue());
                    }
                }
                case ENTITY -> {
                    if (value instanceof Map) {
                        fieldValueDto.setEntityValue(deserializeEntityByMap(
                                fieldDef.getCollectionRef(),
                                (Map<String, Object>) value,
                                entityResolver
                        ));
                    }
                }
                case COLLECTION -> {
                    if (value instanceof Collection) {
                        fieldValueDto.setEntities(((Collection) value).stream()
                                .map(childEntity ->
                                        deserializeEntityByMap(
                                                fieldDef.getCollectionRef(),
                                                (Map<String, Object>) childEntity,
                                                entityResolver
                                        )).toList());
                    }
                }
                case DICTIONARY_EXTERNAL -> {
                    if (value instanceof Collection) {
                        fieldValueDto.setExternalValues(((Collection) value).stream()
                                .map(item -> {
                                    if (item instanceof String) {
                                        return new BaseExternalRef().setId(item.toString());
                                    }
                                    return objectMapper.convertValue(item, BaseExternalRef.class);
                                })
                                .toList());
                    } else {
                        // TODO: с внешними справочниками на текущий момент работа по
                        //  отдельным значениям не поставлена
                        fieldValueDto.setExternalValues(
                                List.of(objectMapper.convertValue(value, BaseExternalRef.class))
                        );
                    }
                }
                case STATUS, DICTIONARY -> {
                    if (!fieldDef.isMultiple()) {
                        var refValue = objToDictionary(
                                value,
                                fieldDef,
                                (Entity e) -> this.convertToDtoWithContext(e, new EntityMappingContext(false))
                        );
                        if (refValue != null) {
                            fieldValueDto.setRefValue(refValue);
                        } else {
                            log.warn("Dictionary value was not found for: {}", value);
                            return null;
                        }
                    } else {
                        if (value instanceof List) {
                            fieldValueDto.setRefValueCollection(
                                    ((List) value).stream()
                                            .map(dictioaryItem ->
                                                    objToDictionary(
                                                            dictioaryItem,
                                                            fieldDef,
                                                            (Entity e) -> this.convertToBaseRef(e)))
                                            .filter(Objects::nonNull)
                                            .toList()
                            );
                        }
                    }
                }
                default -> {
                    // TODO: not implemented
                    log.trace("Can't deserialize value: {}, field def: {}", value, fieldDef.getId());
                }
            }
            return fieldValueDto;
        }
        return null;
    }

    /**
     * Десерилизует из карты значений служебное представление сущности.
     * Используется при подключении внешних источников.
     *
     * @param entityDef описание сущности (дефиниция)
     * @param sourceMap карта значений
     * @return
     */
    public EntityDto deserializeEntityByMap(EntityDef entityDef,
                                            Map<String, Object> sourceMap,
                                            BiFunction<UUID, String, EntityDto> entityResolver
    ) {
        var entityId = Optional.ofNullable(sourceMap.get("id")).map(Object::toString).orElse(null);
        EntityDto entityDto = (entityId != null && entityResolver != null)
                ? entityResolver.apply(entityDef.getId(), entityId)
                : new EntityDto();
        if (entityDto.getEntityDef() == null) {
            entityDto.setEntityDef(new BaseRef().setId(entityDef.getId()));
        }

        // карта существующих значений в сущности
        var currentValueContext = entityDto.getValues().stream()
                .collect(Collectors.toMap(
                        fieldValueDto -> fieldValueDto.getFieldDef().getCode(),
                        Function.identity()));

        entityDto.setValues(
                entityDefUtils.getAllFieldDefs(entityDef.getContainer())
                        .map(fieldDef -> {
                            if (fieldDef.getFieldDefType() == null || fieldDef.getFieldDefType() == FieldDefType.FLEX) {
                                return deserializeFlexField(fieldDef, sourceMap, currentValueContext, entityResolver);
                            }
                            if (fieldDef.getFieldDefType() == FieldDefType.FIXED) {
                                // при обработке служебного (фиксированного) поля FieldValue не создается,
                                // но модифицируется создаваемый DTO сущности
                                deserializeFixedField(entityDto, fieldDef, sourceMap);
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .toList()
        );
        try {
            // расширим вычислимыми полями
            wideEntityByCalculatedProperties(entityDto, new LazyInitializer<>() {
                @Override
                protected Map<String, Object> initialize() {
                    return makeFieldMap(entityDto);
                }
            }, entityDef);
        } catch (ConcurrentException e) {
            log.warn("Ошибка вычисления контекста", e);
        }

        return entityDto;
    }

    /**
     * Расширяет карту значений для DTO сущности по идентификатору
     *
     * @param entityDto
     * @return
     */
    private Map<String, Object> fillFieldMap(
            @Nullable EntityDto entityDto,
            Map<String, Object> res
    ) {
        if (entityDto != null) {
            // служебные поля
            if (entityDto.getCreatedTs() != null) {
                res.put("createdTs", entityDto.getCreatedTs().toOffsetDateTime());
            }
            if (entityDto.getUpdatedTs() != null) {
                res.put("updatedTs", entityDto.getUpdatedTs().toOffsetDateTime());
            }
            // TODO: можно перевести на сборку автоматически, но не у всех сущностей есть сейчас поля (сложно будет отследить что поломается)
            res.put("id", entityDto.getId());
            res.put("slug", entityDto.getSlug());
            res.put("seq", entityDto.getSeq());
            res.put("author", entityDto.getAuthor());
            res.put("authorEmail", entityDto.getAuthorEmail());
            res.put("lastStatusChangeTs",
                    Optional.ofNullable(entityDto.getLastStatusChangeTs()).map(ZonedDateTime::toOffsetDateTime).orElse(null)
            );

            // в целях оптимизации дефиниции полей запрашиваются одним запросом
            var fieldDefMap = fieldDefRepository.findAllById(entityDto.getValues().stream()
                            .map(FieldValueDto::getFieldDef)
                            .map(BaseDto::getId)
                            .filter(Objects::nonNull)
                            .toList()).stream()
                    .collect(Collectors.toMap(FieldDef::getId, Function.identity()));

            entityDto.getValues()
                    .forEach(v -> {
                        var fieldDef = fieldDefMap.get(v.getFieldDef().getId());
                        if (fieldDef != null) {
                            res.put(fieldDef.getCode(), getValue(v, fieldDef));
                        } else {
                            log.warn("FieldDef was not found: {}", v.getFieldDef().getId());
                        }
                    });
        }
        return res;
    }

    /**
     * Расширяет карту значений для сущности по идентификатору
     *
     * @param entity
     * @return
     */
    private Map<String, Object> fillFieldMap(
            @Nullable Entity entity,
            Map<String, Object> res,
            boolean evaluate,
            boolean fullView
    ) {
        if (entity != null) {
            // служебные поля
            if (entity.getCreatedTs() != null) {
                res.put("createdTs", entity.getCreatedTs().toOffsetDateTime());
            }
            if (entity.getUpdatedTs() != null) {
                res.put("updatedTs", entity.getUpdatedTs().toOffsetDateTime());
            }
            if (entity.getLastStatusChangeTs() != null) {
                res.put("lastStatusChangeTs", entity.getLastStatusChangeTs().toOffsetDateTime());
            }
            // TODO: разобраться как хранить обязательные поля
            res.put("id", entity.getId().toString());
            res.put("slug", entity.getSlug());
            res.put("seq", entity.getSeq());
            res.put("author", entity.getAuthor());
            res.put("authorEmail", entity.getAuthorEmail());

            entity.getValues().stream()
                    .filter(v -> fullView || v.getFieldDef().isVisibleListView())
                    .forEach(v ->
                            res.put(v.getFieldDef().getCode(), getValue(v, fullView))
                    );
            // вычислимые поля
            if (evaluate) {
                entityDefUtils.getAllFieldDefs(entity.getEntityDef().getContainer())
                        .filter(fieldDef -> fullView || fieldDef.isVisibleListView())
                        .filter(fieldDef -> StringUtils.isNotBlank(fieldDef.getExpression()))
                        .forEach(fieldDef -> {
                            var processor = processors.stream()
                                    .filter(ep -> ep.getFieldType() == fieldDef.getType())
                                    .findFirst();
                            if (processor.isPresent()) {
                                var calculatedValue = processor.get().evaluate(
                                        fieldDef.getExpression(),
                                        res, //this.makeFieldMap(entity, false, fullView),
                                        fieldDef
                                );
                                if (calculatedValue != null) {
                                    res.put(fieldDef.getCode(), calculatedValue);
                                }
                            }
                        });
            }
        }
        return res;
    }

    /**
     * Строит карту значений для сущности
     *
     * @param entity
     * @return
     */
    public Map<String, Object> makeFieldMap(Entity entity, boolean evaluate, boolean fullView) {
        return fillFieldMap(entity, new HashMap<>(), evaluate, fullView);
    }

    /**
     * Строит карту значений для DTO сущности
     *
     * @param entityDto
     * @return
     */
    public Map<String, Object> makeFieldMap(EntityDto entityDto) {
        return fillFieldMap(entityDto, new HashMap<>());
    }

    /**
     * Безопасная десерилизация json объекта
     *
     * @param value
     * @return
     */
    private Map<String, Object> getJsonValue(String value) {
        if (StringUtils.isNotBlank(value)) {
            try {
                return objectMapper.readValue(value, new TypeReference<>() {
                });
            } catch (Exception e) {
                log.error("Ошибка десерилизации json объекта", e);
            }
        }
        return null;
    }

    /**
     * Ищет значение в которое следует серилизовывать сущность
     *
     * @param enumFieldDefId предварительно найденный идентификатор дефиниции поля для серилизации в enum
     * @param entity
     * @return
     */
    public String getEnumValue(UUID enumFieldDefId, Entity entity) {
        if (entity == null) {
            // такая ситуация может возникнуть у шаблонов
            return null;
        }
        return entity.getValues().stream().filter(value ->
                        value.getFieldDef().getId().equals(enumFieldDefId))
                .map(FieldValue::getTextValue).findFirst().orElse(null);
    }

    /**
     * Выполняет маппинг в карту значений текстовых полей согласно настройкам
     *
     * @param text исходный текст
     * @return
     */
    private String getText(String text) {
        return emptyAsNull
                ? (StringUtils.isEmpty(text) ? null : text)
                : text;
    }

    /**
     * Возвращает значение в виде объекта в зависимости от типа
     *
     * @param fieldValue
     * @return
     */
    public Object getValue(FieldValue fieldValue, boolean fullView) {
        if (fieldValue == null) {
            return null;
        }
        return switch (fieldValue.getFieldDef().getType()) {
            // TODO: объединить с обработкой multiple в IMAGE
            case TEXT, STRING, HTML, COLOR, SLUG, VIDEO, LINK -> getText(fieldValue.getTextValue());
            case IMAGE ->
                    fieldValue.getFieldDef().isMultiple() ? fieldValue.getArrayText() : getText(fieldValue.getTextValue());
            case JSON -> getJsonValue(fieldValue.getTextValue());
            case BOOLEAN -> fieldValue.getBooleanValue();
            case TAGS -> fieldValue.getArrayText();
            case MAP -> fieldValue.getGeometryValues().stream()
                    .map(fieldGeometryValueConverter::convertToDto)
                    .toList();
            case ENTITY -> makeFieldMap(fieldValue.getEntityValue(), true, fullView);
            case INT -> fieldValue.getIntValue();
            case DATE -> (fieldValue.getFieldDef().isMultiple()
                    ? fieldValue.getArrayDate()
                    : fieldValue.getDateValue());
            case TIME -> fieldValue.getTimeValue();
            case DATETIME -> (fieldValue.getFieldDef().isMultiple()
                    ? fieldValue.getArrayDateTime()
                    : fieldValue.getDatetimeValue());
            case DOUBLE -> fieldValue.getDoubleValue();
            case COLLECTION -> fieldValue.getEntities().stream()
                    .map(entity -> makeFieldMap(entity, true, fullView))
                    .toList();
            case STATUS, DICTIONARY -> {
                var fieldEnumSerialized = entityDefUtils.getAllFieldDefs(fieldValue.getFieldDef().getCollectionRef().getContainer())
                        .filter(fieldDefInner -> Boolean.TRUE.equals(fieldDefInner.getSerializeEnum()))
                        .findFirst();
                // если ключевое поле представлено и нет флага полной растеризации то возвращаем тольок ключ
                if (fieldEnumSerialized.isPresent()
                        && !fieldValue.getFieldDef().getSerializeFull()) {
                    if (!fieldValue.getFieldDef().isMultiple()) {
                        yield getEnumValue(fieldEnumSerialized.get().getId(), fieldValue.getRefValue());
                    } else {
                        yield fieldValue.getRefValueCollection().stream()
                                .map(entity -> getEnumValue(fieldEnumSerialized.get().getId(), entity))
                                .toList();
                    }
                } else {
                    if (!fieldValue.getFieldDef().isMultiple()) {
                        yield makeFieldMap(fieldValue.getRefValue(), true, fullView);
                    } else {
                        yield fieldValue.getRefValueCollection().stream()
                                .map(entity -> makeFieldMap(entity, true, fullView))
                                .toList();
                    }
                }
            }
            case DICTIONARY_EXTERNAL -> fieldValue.getExternalValues().stream()
                    .map(dictionaryExternalValueConverter::convertToDto)
                    .toList();
            default -> null;
        };
    }

    /**
     * Возвращает значение в виде объекта в зависимости от типа
     *
     * @param fieldValueDto
     * @return
     */
    public Object getValue(FieldValueDto fieldValueDto, FieldDef fieldDef) {
        if (fieldValueDto == null) {
            return null;
        }
        return switch (fieldDef.getType()) {
            case TEXT, STRING, HTML, COLOR, SLUG, LINK, URL, VIDEO -> fieldValueDto.getTextValue();
            case IMAGE -> fieldDef.isMultiple() ? fieldValueDto.getArrayText() : fieldValueDto.getTextValue();
            case JSON -> getJsonValue(fieldValueDto.getTextValue());
            case BOOLEAN -> fieldValueDto.getBooleanValue();
            case INT -> fieldValueDto.getIntValue();
            case DATE -> (fieldDef.isMultiple() ? fieldValueDto.getArrayDate() : fieldValueDto.getDateValue());
            case TIME -> fieldValueDto.getTimeValue();
            case DATETIME ->
                    (fieldDef.isMultiple() ? fieldValueDto.getArrayDateTime() : fieldValueDto.getDatetimeValue());
            case DOUBLE -> fieldValueDto.getDoubleValue();
            case MAP -> fieldValueDto.getGeometryValues();
            case COLLECTION -> fieldValueDto.getEntities().stream()
                    .map(this::makeFieldMap)
                    .toList();
            case STATUS -> makeFieldMap(fieldValueDto.getRefValue());
            case DICTIONARY -> {
                if (!fieldDef.isMultiple()) {
                    yield makeFieldMap(fieldValueDto.getRefValue());
                } else {
                    yield fieldValueDto.getRefValueCollection();
                }
            }
            case DICTIONARY_EXTERNAL -> fieldValueDto.getExternalValues();
            case TAGS -> fieldValueDto.getArrayText();
            case ENTITY -> makeFieldMap(fieldValueDto.getEntityValue());
            default -> null;
        };
    }

    /**
     * Сборка списочного прикладного представления
     *
     * @param entity
     * @return
     */
    public Map<String, Object> mapShort(Entity entity) {
        return fillFieldMap(entity, new HashMap<>(), true, false);
    }

    /**
     * Сборка прикладного представления
     *
     * @param entity
     * @return
     */
    public Map<String, Object> mapFull(Entity entity) {
        return fillFieldMap(entity, new HashMap<>(), true, true);
    }

    @Named(ENTITY_COPY)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTs", ignore = true)
    @Mapping(target = "updatedTs", ignore = true)
    @Mapping(target = "values", qualifiedByName = FIELD_VALUE_COPY)
    public abstract EntityDto mapWithoutId(Entity entity);
}
