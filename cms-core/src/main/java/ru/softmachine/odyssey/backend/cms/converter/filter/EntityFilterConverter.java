package ru.softmachine.odyssey.backend.cms.converter.filter;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import ru.softmachine.odyssey.backend.cms.config.ConverterConfig;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseStringRef;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;
import ru.softmachine.odyssey.backend.cms.dto.filter.EntityFilterDto;
import ru.softmachine.odyssey.backend.cms.dto.filter.FieldFilterBlockDto;
import ru.softmachine.odyssey.backend.cms.dto.filter.FieldFilterDto;
import ru.softmachine.odyssey.backend.cms.dto.filter.FieldOrderDto;
import ru.softmachine.odyssey.backend.cms.dto.filter.FilterGlobalOperator;
import ru.softmachine.odyssey.backend.cms.dto.filter.FilterOperator;
import ru.softmachine.odyssey.backend.cms.dto.filter.SearchFilterDto;
import ru.softmachine.odyssey.backend.cms.dto.field.FieldDefType;
import ru.softmachine.odyssey.backend.cms.exception.EntityNotFoundException;
import ru.softmachine.odyssey.backend.cms.repository.EntityDefRepository;
import ru.softmachine.odyssey.backend.cms.repository.FieldDefRepository;
import ru.softmachine.odyssey.backend.cms.service.entity.EntityService;
import ru.softmachine.odyssey.backend.cms.utils.EntityDefUtils;
import ru.softmachine.odyssey.backend.model.UprCmsEntityRequest;
import ru.softmachine.odyssey.backend.model.UprCmsFieldFilter;
import ru.softmachine.odyssey.backend.model.UprCmsFieldFilterOperation;
import ru.softmachine.odyssey.backend.model.UprCmsGlobalFilterOperation;
import ru.softmachine.odyssey.backend.model.UprCmsTextSearchFilter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Mapper(config = ConverterConfig.class)
public abstract class EntityFilterConverter {

    @Autowired
    private EntityDefRepository entityDefRepository;
    @Autowired
    private FieldDefRepository fieldDefRepository;
    @Autowired
    private EntityService entityService;
    @Autowired
    private EntityDefUtils entityDefUtils;

    public FilterOperator getOperator(UprCmsFieldFilterOperation operation) {
        return switch (operation) {
            case EQUAL -> FilterOperator.EQUAL;
            case GT -> FilterOperator.GT;
            case GTE -> FilterOperator.GTE;
            case LT -> FilterOperator.LT;
            case LTE -> FilterOperator.LTE;
            case LIKE -> FilterOperator.LIKE;
            case ILIKE -> FilterOperator.LIKE;
            case INTERSECT -> FilterOperator.INTERSECT;
            case IN -> FilterOperator.IN;
        };
    }

    /**
     * Преобразует условия по полю в служебное представление
     *
     * @param entityDefId        идентификатор дефиниции
     * @param filedFilterRequest фильтр прикладного представления
     * @return фильтр служебного представления
     */
    private FieldFilterDto convertFieldDto(UUID entityDefId, UprCmsFieldFilter filedFilterRequest) {
        // поле по которому фильтруется значение
        var fieldDefId = fieldDefRepository.getFieldDefIdByCode(entityDefId,
                        filedFilterRequest.getCode())
                .orElseThrow(() -> new EntityNotFoundException("Field def was not found",
                        filedFilterRequest.getCode()));
        var fieldDef = fieldDefRepository.findById(fieldDefId)
                .orElseThrow(() -> new EntityNotFoundException("Field def was not found", fieldDefId.toString()));

        // фильтр по текстовому полю
        if (filedFilterRequest.getTextValue() != null) {
            return new FieldFilterDto()
                    .setFieldDef(new BaseRef().setId(fieldDef.getId()))
                    .setOperator(Optional.ofNullable(filedFilterRequest.getOperation())
                            .map(this::getOperator)
                            .orElse(FilterOperator.EQUAL))
                    .setTextValue(filedFilterRequest.getTextValue());
        }

        // фильтр по логическому значению
        if (filedFilterRequest.getBooleanValue() != null) {
            return new FieldFilterDto()
                    .setFieldDef(new BaseRef().setId(fieldDef.getId()))
                    .setOperator(Optional.ofNullable(filedFilterRequest.getOperation())
                            .map(this::getOperator)
                            .orElse(FilterOperator.EQUAL))
                    .setBooleanValue(filedFilterRequest.getBooleanValue());
        }

        // фильтр пересечения коллекций
        if (filedFilterRequest.getArrayTextValue() != null) {
            return new FieldFilterDto()
                    .setFieldDef(new BaseRef().setId(fieldDef.getId()))
                    .setOperator(Optional.ofNullable(filedFilterRequest.getOperation())
                            .map(this::getOperator)
                            .orElse(FilterOperator.EQUAL))
                    .setArrayText(filedFilterRequest.getArrayTextValue());
        }

        // фильтр коллекции внешних справочников
        if (filedFilterRequest.getArrayExternalValueRef() != null) {
            return new FieldFilterDto()
                    .setFieldDef(new BaseRef().setId(fieldDef.getId()))
                    .setOperator(Optional.ofNullable(filedFilterRequest.getOperation())
                            .map(this::getOperator)
                            .orElse(FilterOperator.INTERSECT))
                    .setExternalDictionaryValues(
                            filedFilterRequest.getArrayExternalValueRef().stream().map(item -> {
                                var ref = new BaseStringRef();
                                ref.setId(item.getId());
                                ref.setName(item.getName());
                                return ref;
                            }).toList());
        }

        // фильтр внешних справочников
        if (filedFilterRequest.getExternalValueRef() != null) {
            return new FieldFilterDto()
                    .setFieldDef(new BaseRef().setId(fieldDef.getId()))
                    .setOperator(Optional.ofNullable(filedFilterRequest.getOperation())
                            .map(this::getOperator)
                            .orElse(FilterOperator.EQUAL))
                    .setExternalRefValue(new BaseStringRef(filedFilterRequest.getExternalValueRef().getId())
                            .setName(filedFilterRequest.getExternalValueRef().getName()));
        }

        // фильтр по date time
        if (filedFilterRequest.getDateTimeValue() != null) {
            return new FieldFilterDto()
                    .setFieldDef(new BaseRef().setId(fieldDef.getId()))
                    .setOperator(Optional.ofNullable(filedFilterRequest.getOperation())
                            .map(this::getOperator)
                            .orElse(FilterOperator.EQUAL))
                    .setDateTimeValue(filedFilterRequest.getDateTimeValue().toZonedDateTime());
        }

        // фильтр по ссылке
        if (filedFilterRequest.getValueRef() != null) {
            // поле по которому нужно найти сущность идентифицированную значением в refValue
            var fieldDefInnerId = fieldDefRepository.getFieldDefIdByCode(
                            fieldDef.getCollectionRef().getId(),
                            filedFilterRequest.getValueRef().getIdentifiedBy())
                    .orElseThrow(() -> new EntityNotFoundException("Field def was not found",
                            filedFilterRequest.getValueRef().getIdentifiedBy()));
            var identifierField = fieldDefRepository.findById(fieldDefInnerId)
                    .orElseThrow(() -> new EntityNotFoundException("Field def was not found", fieldDefInnerId.toString()));

            var entities = entityService.getAllEntityRefByEntityDefCode(
                    fieldDef.getCollectionRef().getCode(),
                    new EntityFilterDto()
                            .setFieldFilters(List.of(new FieldFilterDto()
                                    .setFieldDef(new BaseRef().setId(identifierField.getId()))
                                    .setTextValue(filedFilterRequest.getValueRef().getValue())
                                    .setOperator(FilterOperator.EQUAL))),
                    PageRequest.of(0, 1)
            );
            if (entities.isEmpty()) {
                throw new EntityNotFoundException(
                        String.format("Сущность идентифицируемая как %s не найдена", filedFilterRequest.getValueRef().getValue()),
                        filedFilterRequest.getValueRef().getValue());
            }

            return new FieldFilterDto()
                    .setFieldDef(new BaseRef().setId(fieldDef.getId()))
                    .setOperator(FilterOperator.EQUAL) // на текущий момент поддерживается только equal
                    .setRefValue(new BaseRef().setId(entities.getFirst().getId()));
        }

        // фильтр по ссылкам
        if (filedFilterRequest.getArrayValueRef() != null) {
            // поле по которому нужно найти сущность идентифицированную значением в arrayValueRef
            var fieldDefInnerId = fieldDefRepository.getFieldDefIdByCode(
                            fieldDef.getCollectionRef().getId(),
                            filedFilterRequest.getArrayValueRef().getIdentifiedBy())
                    .orElseThrow(() -> new EntityNotFoundException("Field def was not found",
                            filedFilterRequest.getArrayValueRef().getIdentifiedBy()));
            var identifierField = fieldDefRepository.findById(fieldDefInnerId)
                    .orElseThrow(() -> new EntityNotFoundException("Field def was not found", fieldDefInnerId.toString()));

            var values = filedFilterRequest.getArrayValueRef().getValues();
            var entities = entityService.getAllEntityRefByEntityDefCode(
                    fieldDef.getCollectionRef().getCode(),
                    new EntityFilterDto()
                            .setFieldFilters(List.of(new FieldFilterDto()
                                    .setFieldDef(new BaseRef().setId(identifierField.getId()))
                                    .setArrayText(values)
                                    .setOperator(FilterOperator.IN))),
                    null
            );
            if (entities.isEmpty()) {
                throw new EntityNotFoundException(String.format("Сущности %s не найдены", values), null);
            }

            return new FieldFilterDto()
                    .setFieldDef(new BaseRef().setId(fieldDef.getId()))
                    .setOperator(FilterOperator.IN)
                    .setDictionaryValues(entities);
        }
        log.warn("Параметры фильтра не поддерживаются: {}", filedFilterRequest);
        return null;
    }

    private FilterGlobalOperator convertOperator(UprCmsGlobalFilterOperation operation) {
        if (operation == UprCmsGlobalFilterOperation.OR) {
            return FilterGlobalOperator.OR;
        } else {
            return FilterGlobalOperator.AND;
        }
    }

    /**
     * Конвертирует фильтр от UPR BFF собранный на кодах полей и дефиниций (они инварианты на разных окружениях)
     * во внутренний фильтр сущностей.
     *
     * @param entityDefCode
     * @param request
     * @return
     */
    public EntityFilterDto convertToDto(
            String entityDefCode,
            UprCmsTextSearchFilter textSearch,
            UprCmsEntityRequest request
    ) {
        EntityFilterDto entityFilterDto = new EntityFilterDto();

        // глобальный оператор объединения условий
        entityFilterDto.setGlobalOperator(convertOperator(request.getGlobalOperator()));

        // вычисляем по коду дефиницию сущности для которой задается фильтр
        var entityDef = entityDefRepository.getEntityDefByCode(entityDefCode).orElseThrow(
                () -> new EntityNotFoundException(String.format("Entity Def with code %s not found", entityDefCode), entityDefCode)
        );

        // текстовый поиск
        if (textSearch != null) {
            entityFilterDto.setSearchFilter(new SearchFilterDto(
                    textSearch.getSearch(),
                    textSearch.getFtsSearch(),
                    entityDefUtils.getAllFieldDefs(entityDef.getContainer())
                            .filter(FieldDef::isUseSearchFilter)
                            .map(fd -> new BaseRef().setId(fd.getId()))
                            .toList()

            ));
        }

        // сортировка
        if (request != null && request.getFieldOrder() != null) {
            // поле по которому сортируются значения
            var fieldDefIdWithType = fieldDefRepository.getFieldDefIdAndTypeByCode(
                            entityDef.getId(), request.getFieldOrder().getCode())
                    .orElseThrow(() -> new EntityNotFoundException("Field def was not found",
                            request.getFieldOrder().getCode()));
            var fieldOrder = new FieldOrderDto()
                    .setDescending(
                            Optional.ofNullable(
                                            request.getFieldOrder().getDescending())
                                    .orElse(false))
                    .setFieldDef(new BaseRef().setId(fieldDefIdWithType.getId()));
            if (fieldDefIdWithType.getType() == FieldDefType.FLEX) {
                entityFilterDto.setFieldOrder(fieldOrder);
            } else {
                entityFilterDto.setFixedFieldOrder(fieldOrder);
            }
        }

        // фильтры
        if (request.getFieldFilter() != null) {
            entityFilterDto.setFieldFilters(request.getFieldFilter().stream()
                    .map(filedFilterRequest -> convertFieldDto(entityDef.getId(), filedFilterRequest))
                    .filter(Objects::nonNull)
                    .toList());
        }

        // блоки фильтров
        if (request.getFieldFilterBlock() != null) {
            entityFilterDto.setFieldFilterBlocks(request.getFieldFilterBlock().stream()
                    .map(filedFilterBlock -> new FieldFilterBlockDto()
                            .setOperator(convertOperator(filedFilterBlock.getOperator()))
                            .setFieldFilters(filedFilterBlock.getFieldFilter().stream()
                                    .map(filedFilterRequest -> convertFieldDto(entityDef.getId(), filedFilterRequest))
                                    .filter(Objects::nonNull)
                                    .toList()))
                    .filter(Objects::nonNull)
                    .toList());
        }

        return entityFilterDto;
    }
}
