package ru.softmachine.odyssey.backend.cms.validation;

import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.converter.EntityConverter;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseDto;
import ru.softmachine.odyssey.backend.cms.dto.base.FieldType;
import ru.softmachine.odyssey.backend.cms.dto.validation.ConstraintViolation;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;
import ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation;
import ru.softmachine.odyssey.backend.cms.dto.validation.ValidationType;
import ru.softmachine.odyssey.backend.cms.exception.EntityNotFoundException;
import ru.softmachine.odyssey.backend.cms.repository.EntityDefRepository;
import ru.softmachine.odyssey.backend.cms.repository.FieldDefRepository;
import ru.softmachine.odyssey.backend.cms.repository.FieldValueRepository;
import ru.softmachine.odyssey.backend.cms.utils.EntityDefUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@Slf4j
@Component
@RequiredArgsConstructor
public class ValidationManager {

    private final List<Validator> validatorsList;
    private final Map<ValidationType, Validator> validators = new ConcurrentHashMap<>();
    private final FieldDefRepository fieldDefRepository;
    private final EntityConverter entityConverter;
    private final FieldValueRepository fieldValueRepository;
    private final EntityDefRepository entityDefRepository;
    private final EntityDefUtils entityDefUtils;

    @PostConstruct
    private void postConstruct() {
        validatorsList.forEach(validator -> {
            validator.getValidationTypes().forEach(validationType -> {
                validators.put(validationType, validator);
            });
        });
    }

    /**
     * Выполняет валидацию рутовой сузности
     *
     * @param entity DTO сущности
     * @return набор выявленных ошибок
     */
    @Transactional
    public List<ConstraintViolation> validate(EntityDto entity) {
        return validateInner(entity, Map.of(), "", null)
                .toList();
    }

    /**
     * Выполняет валидацию переданной DTO согласно настройкам в дефиниции полей,
     * принимает на вход контекст значений для вычислимых ограничений, текущий path сущности.
     *
     * @param entity DTO сущности
     * @return набор выявленных ошибок
     */
    private Stream<ConstraintViolation> validateInner(
            EntityDto entity,
            Map<String, Object> parentEntityContext,
            String path,
            @Nullable FieldDef parentFieldDef
    ) {
        if (entity == null) {
            return Stream.of();
        }
        // контекст для выполнения расчетов
        var entityContext = new HashMap<>(parentEntityContext);
        entityContext.putAll(entityConverter.makeFieldMap(entity));

        // в целях оптимизации дефиниции полей запрашиваются одним запросом
        var fieldDefMap = fieldDefRepository.findAllById(entity.getValues().stream()
                        .map(FieldValueDto::getFieldDef)
                        .map(BaseDto::getId)
                        .filter(Objects::nonNull)
                        .toList()).stream()
                .collect(Collectors.toMap(FieldDef::getId, Function.identity()));

        // проверка уникальности
        var uniqueFields = fieldDefMap.values().stream()
                .filter(fieldDef ->
                        fieldDef.getFieldValidations().stream()
                                .anyMatch(validation -> validation.getType() == ValidationType.UNIQUE)
                ).toList();
        if (!uniqueFields.isEmpty()) {
            // случай рутовой сущности и глобальной уникальности
            if (parentFieldDef == null) {
                if (uniqueFields.size() == 1) {
                    var uniqueValue = entity.getValues().stream()
                            .filter(value -> value.getFieldDef().getId().equals(uniqueFields.getFirst().getId()))
                            .findAny();
                    // если значение не передано не проверяем на уникальность
                    if (uniqueValue.isPresent()) {
                        if (fieldValueRepository.checkUnique(
                                uniqueFields.getFirst().getId(),
                                uniqueValue
                                        .map(FieldValueDto::getId)
                                        .orElse(null),
                                uniqueValue.get().getTextValue() // TODO: пока только textValue
                        )) {
                            var message = uniqueFields.getFirst().getFieldValidations().stream()
                                    .filter(validation -> validation.getType() == ValidationType.UNIQUE)
                                    .map(FieldValidation::getMessage)
                                    .filter(Objects::nonNull)
                                    .findFirst()
                                    .orElse("'" + uniqueValue.get().getTextValue() + "' not unique");

                            return Stream.of(new ConstraintViolation(
                                    message,
                                    path + "-" + uniqueFields.getFirst().getCode(), ValidationType.UNIQUE));
                        }
                    }
                } else {
                    // TODO: not supported
                }
            }  // случай внутренней коллекции и уникальности в рамках этой коллекции проверяется на уровне полей коллекции
        }

        return entity.getValues().stream()
                .flatMap(value -> validateValue(value, entityContext, fieldDefMap, path));
    }

    /**
     * Вычисляет все огранчения для значения поля
     *
     * @param valueDto      значени поля
     * @param entityContext конеткст для вычислимых ограничений
     * @param fieldDefs     коллекция предварительно вычисленных дефиниций полей
     * @param path          текущий путь до поля
     * @return поток ошибок валидации
     */
    private Stream<ConstraintViolation> validateValue(
            FieldValueDto valueDto,
            Map<String, Object> entityContext,
            Map<UUID, FieldDef> fieldDefs,
            String path
    ) {
        var fieldDef = fieldDefs.get(valueDto.getFieldDef().getId());
        if (fieldDef != null) {
            // вложенные сущности
            if (fieldDef.getType() == FieldType.ENTITY) {
                return validateInner(
                        valueDto.getEntityValue(),
                        entityContext,
                        path + "-" + fieldDef.getCode(),
                        fieldDef
                );
            }
            // вложенные коллекции
            if (fieldDef.getType() == FieldType.COLLECTION) {
                if (CollectionUtils.isEmpty(valueDto.getEntities())) {
                    return Stream.of();
                }
                var collectionEntityDef = entityDefRepository.findById(fieldDef.getCollectionRef().getId());
                if (collectionEntityDef.isEmpty()) {
                    return Stream.of();
                }
                // TODO: веротяно стоит перенести в коллекцию и перечислять поля там
                var uniqueFieldIds = entityDefUtils.getAllFieldDefs(collectionEntityDef.get().getContainer())
                        .filter(fieldDefCollection ->
                                fieldDefCollection.getFieldValidations().stream()
                                        .anyMatch(validation -> validation.getType() == ValidationType.UNIQUE))
                        .map(FieldDef::getId)
                        .toList();
                // Для фильтра по нескольким полям будет взято первое попавшееся сообщение, что не очень хорошо
                var message = entityDefUtils.getAllFieldDefs(collectionEntityDef.get().getContainer())
                        .map(fieldDefCollection ->
                                fieldDefCollection.getFieldValidations().stream()
                                        .filter(validation -> validation.getType() == ValidationType.UNIQUE)
                                        .findFirst().orElse(null))
                        .filter(Objects::nonNull)
                        .findFirst()
                        .map(FieldValidation::getMessage)
                        .orElse("Duplicate found");

                // в целях оптимизации дефиниции полей запрашиваются одним запросом
                var innerCollectionFieldDefMap = entityDefUtils.getAllFieldDefs(collectionEntityDef.get().getContainer())
                        .collect(Collectors.toMap(FieldDef::getId, Function.identity()));

                if (!uniqueFieldIds.isEmpty()) {
                    // валидация уникальный значений
                    var sortedUniqueKeys = valueDto.getEntities().stream()
                            .map(innerEntityDto ->
                                    innerEntityDto.getValues().stream()
                                            .filter(value -> uniqueFieldIds.contains(value.getFieldDef().getId()))
                                            // сгодится любая сортировка по дефинициям
                                            .sorted(Comparator.comparing(v -> v.getFieldDef().getId().toString()))
                                            .map(fieldValueDto ->
                                                    entityConverter.getValue(
                                                            fieldValueDto,
                                                            innerCollectionFieldDefMap.get(fieldValueDto.getFieldDef().getId())))
                                            .toList()
                            ).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

                    // если по ключу больше одного значения - то это дубликат
                    var duplicates = sortedUniqueKeys.entrySet().stream()
                            .filter(entry -> entry.getValue() > 1)
                            .map(Map.Entry::getKey)
                            .toList();
                    if (!duplicates.isEmpty()) {
                        return duplicates.stream()
                                .flatMap(keys ->
                                        IntStream.range(0, valueDto.getEntities().size() - 1)
                                                .mapToObj(i -> {
                                                            var keyFields = valueDto.getEntities().get(i).getValues().stream()
                                                                    .filter(value -> uniqueFieldIds.contains(value.getFieldDef().getId()))
                                                                    // сгодится любая сортировка по дефинициям
                                                                    .sorted(Comparator.comparing(v -> v.getFieldDef().getId().toString()))
                                                                    .toList();
                                                            if (keyFields.stream().map(fieldValueDto ->
                                                                            entityConverter.getValue(
                                                                                    fieldValueDto,
                                                                                    innerCollectionFieldDefMap.get(
                                                                                            fieldValueDto.getFieldDef().getId())))
                                                                    .toList().equals(keys)) {
                                                                return keyFields.stream().map(field ->
                                                                        new ConstraintViolation(
                                                                                message,
                                                                                path + "-" +
                                                                                        fieldDef.getCode() + "-" +
                                                                                        i + "-" +
                                                                                        field.getFieldDef().getCode(),
                                                                                ValidationType.UNIQUE));
                                                            }
                                                            return null;
                                                        }
                                                ).filter(Objects::nonNull)
                                                .flatMap(Function.identity())
                                );
                    }
                }

                return IntStream.range(0, valueDto.getEntities().size())
                        .mapToObj(i ->
                                validateInner(
                                        valueDto.getEntities().get(i),
                                        entityContext,
                                        path + "-" + fieldDef.getCode() + "-" + i,
                                        fieldDef
                                ))
                        .flatMap(Function.identity());
            }
            return fieldDef.getFieldValidations().stream()
                    .map(validation -> validate(
                            validation,
                            valueDto,
                            entityContext,
                            path + "-" + fieldDef.getCode()))
                    .filter(Objects::nonNull);
        }
        throw new EntityNotFoundException(
                "Field Def was not found in FieldValue",
                valueDto.getFieldDef().getId().toString());
    }

    /**
     * Валидирует поле согласно конкретному правилу
     *
     * @param fieldValidation правило
     * @param fieldValue      значение поля
     * @param entityContext   контекст исполнения
     * @param path            полный путь поля
     * @return ошибку валидации или null, если ошибок не выявлено
     */
    private ConstraintViolation validate(
            FieldValidation fieldValidation,
            FieldValueDto fieldValue,
            Map<String, Object> entityContext,
            String path
    ) {
        var validator = validators.get(fieldValidation.getType());

        if (validator == null) {
            log.warn("No such validator: {}", fieldValidation.getType());
            return null;
        }

        return validator.validate(fieldValidation, fieldValue, entityContext, path);
    }
}
