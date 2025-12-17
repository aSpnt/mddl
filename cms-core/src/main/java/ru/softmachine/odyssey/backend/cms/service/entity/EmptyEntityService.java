package ru.softmachine.odyssey.backend.cms.service.entity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.softmachine.odyssey.backend.cms.converter.CommonBaseRefConverter;
import ru.softmachine.odyssey.backend.cms.converter.EntityConverter;
import ru.softmachine.odyssey.backend.cms.converter.FieldValueUtilsConverter;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;
import ru.softmachine.odyssey.backend.cms.evaluation.spel.SimpleEvaluationProcessor;
import ru.softmachine.odyssey.backend.cms.exception.EntityNotFoundException;
import ru.softmachine.odyssey.backend.cms.repository.EntityDefRepository;
import ru.softmachine.odyssey.backend.cms.repository.EntityRepository;
import ru.softmachine.odyssey.backend.cms.repository.EntityTemplateRepository;
import ru.softmachine.odyssey.backend.cms.utils.EntityDefUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Управляет созданием дефолтного (минимального) entity по настройкам дефиниции и шаблонам
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmptyEntityService {

    private final EntityRepository entityRepository;
    private final EntityDefRepository entityDefRepository;
    private final EntityTemplateRepository entityTemplateRepository;
    private final EntityDefUtils entityDefUtils;
    private final FieldValueUtilsConverter fieldValueUtilsConverter;
    private final CommonBaseRefConverter commonBaseRefConverter;
    private final EntityConverter entityConverter;
    private final SimpleEvaluationProcessor evaluationService;

    /**
     * Формирует базовое значение для создаваемой сущности
     * на основе внешнего контекста уже заполненных значений, умолчаний дефиниции (идентифицируемой по коджу) и шаблона
     *
     * @param context
     * @param entityDefCode    код дефиниции
     * @param entityTemplateId
     * @return
     */
    @Transactional(readOnly = true)
    public EntityDto getEmptyEntityByDefCode(
            Map<String, Object> context,
            String entityDefCode,
            @Nullable UUID entityTemplateId
    ) {
        var entityDef = entityDefRepository.getEntityDefByCode(entityDefCode)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefCode));
        return getEmptyEntity(context, entityDef.getId(), entityTemplateId);
    }

    /**
     * Формирует базовое значение для создаваемой сущности
     * на основе внешнего контекста уже заполненных значений, умолчаний дефиниции и шаблона
     *
     * @param context          карта значений контекста запроса
     * @param entityDefId
     * @param entityTemplateId
     * @return
     */
    @Transactional(readOnly = true)
    public EntityDto getEmptyEntity(
            Map<String, Object> context,
            UUID entityDefId,
            @Nullable UUID entityTemplateId
    ) {
        var result = new EntityDto()
                .setEntityDef(new BaseRef().setId(entityDefId))
                .setSeq(entityRepository.getTotalCount(entityDefId));
        var entityDef = entityDefRepository.findById(entityDefId)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefId.toString()));

        // умолчания из шаблона
        var template =
                entityTemplateId != null ? entityTemplateRepository.getByIdAndEntityDefId(
                        entityDefId,
                        entityTemplateId
                ).orElseThrow(() -> new EntityNotFoundException(
                        "Entity Template was not found in Entity Def " + entityDefId,
                        entityTemplateId.toString())) : null;
        if (template != null) {
            //  сохраняем название шаблона
            result.setEntityTemplateName(template.getName());
        }
        var templateValues = template == null ? List.<FieldValueDto>of() :
                template.getValues().stream()
                        .map(fieldValueUtilsConverter::mapWithoutId)
                        .toList();


        // умолчания из дефиниции (менее приоритетные)
        var defaultValues = entityDefUtils.getAllFieldDefs(entityDef.getContainer())
                .map(fieldDef -> {
                    // если проставлено значение через шаблон - игнорируем дефолты из дефиниции
                    if (templateValues.stream().filter(v ->
                            v.getFieldDef().getId().equals(fieldDef.getId())
                    ).findAny().isEmpty()) {
                        return switch (fieldDef.getType()) {
                            case JSON -> {
                                if (fieldDef.getDefaultTextValue() != null && fieldDef.isCreateDefault()) {
                                    var res = evaluationService.evaluate(fieldDef.getDefaultTextValue(), context);
                                    yield new FieldValueDto()
                                            .setFieldDef(commonBaseRefConverter.convertToBaseFieldDef(fieldDef))
                                            .setTextValue(Optional.ofNullable(res)
                                                    .map(Object::toString)
                                                    .orElse(fieldDef.getDefaultTextValue()));
                                }
                                yield null;
                            }
                            case TEXT, STRING, HTML, COLOR, SLUG -> {
                                if (fieldDef.getDefaultTextValue() != null) {
                                    var res = evaluationService.evaluate(fieldDef.getDefaultTextValue(), context);
                                    yield new FieldValueDto()
                                            .setFieldDef(commonBaseRefConverter.convertToBaseFieldDef(fieldDef))
                                            .setTextValue(Optional.ofNullable(res)
                                                    .map(Object::toString)
                                                    .orElse(fieldDef.getDefaultTextValue()));
                                }
                                yield null;
                            }
                            case BOOLEAN -> {
                                if (fieldDef.getDefaultBooleanValue() != null) {
                                    yield new FieldValueDto()
                                            .setFieldDef(commonBaseRefConverter.convertToBaseFieldDef(fieldDef))
                                            .setBooleanValue(fieldDef.getDefaultBooleanValue());
                                }
                                yield null;
                            }
                            case INT -> {
                                if (fieldDef.getDefaultIntValue() != null) {
                                    yield new FieldValueDto()
                                            .setFieldDef(commonBaseRefConverter.convertToBaseFieldDef(fieldDef))
                                            .setIntValue(fieldDef.getDefaultIntValue());
                                }
                                yield null;
                            }
                            case DOUBLE -> {
                                if (fieldDef.getDefaultDoubleValue() != null) {
                                    yield new FieldValueDto()
                                            .setFieldDef(commonBaseRefConverter.convertToBaseFieldDef(fieldDef))
                                            .setDoubleValue(fieldDef.getDefaultDoubleValue());
                                }
                                yield null;
                            }
                            case STATUS, DICTIONARY -> {
                                if (!fieldDef.isMultiple()) {
                                    if (fieldDef.getDefaultRefValue() != null) {
                                        yield new FieldValueDto()
                                                .setFieldDef(commonBaseRefConverter.convertToBaseFieldDef(fieldDef))
                                                .setRefValue(entityConverter.convertToDto(fieldDef.getDefaultRefValue()));
                                    }
                                } else {
                                    // TODO: not implemented
                                }
                                yield null;
                            }
                            case COLLECTION -> {
                                if (fieldDef.isRequired() && fieldDef.isCreateDefault() && fieldDef.getCollectionRef() != null) {
                                    yield new FieldValueDto()
                                            .setFieldDef(commonBaseRefConverter.convertToBaseFieldDef(fieldDef))
                                            .setEntities(List.of(
                                                    getEmptyEntity(
                                                            context,
                                                            fieldDef.getCollectionRef().getId(),
                                                            null
                                                    )
                                            ));
                                }
                                yield null;
                            }
                            case ENTITY -> new FieldValueDto()
                                    .setFieldDef(commonBaseRefConverter.convertToBaseFieldDef(fieldDef))
                                    .setEntityValue(
                                            getEmptyEntity(
                                                    context,
                                                    fieldDef.getCollectionRef().getId(),
                                                    null
                                            ));
                            // TODO: not implemented
                            default -> null;
                        };
                    }
                    return null;
                }).filter(Objects::nonNull)
                .toList(); // можно пропустить

        // объединение дефолтных значений из шаблона и дефиниции
        result.setValues(Stream.concat(
                templateValues.stream(),
                defaultValues.stream()
        ).toList());

        return result;
    }
}
