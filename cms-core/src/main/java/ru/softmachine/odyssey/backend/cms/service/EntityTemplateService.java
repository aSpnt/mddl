package ru.softmachine.odyssey.backend.cms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.softmachine.odyssey.backend.cms.converter.EntityTemplateConverter;
import ru.softmachine.odyssey.backend.cms.dto.EntityTemplateDto;
import ru.softmachine.odyssey.backend.cms.dto.filter.EntityTemplateFilterDto;
import ru.softmachine.odyssey.backend.cms.entity.EntityDefGroup_;
import ru.softmachine.odyssey.backend.cms.entity.EntityTemplate;
import ru.softmachine.odyssey.backend.cms.entity.EntityTemplate_;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity_;
import ru.softmachine.odyssey.backend.cms.exception.EntityNotFoundException;
import ru.softmachine.odyssey.backend.cms.repository.EntityDefRepository;
import ru.softmachine.odyssey.backend.cms.repository.EntityTemplateRepository;
import ru.softmachine.odyssey.backend.cms.utils.SlugUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.softmachine.odyssey.backend.cms.entity.view.EntityDefRefFieldDef_.entityDefId;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EntityTemplateService {

    private final EntityTemplateRepository entityTemplateRepository;
    private final EntityDefRepository entityDefRepository;
    private final EntityTemplateConverter entityTemplateConverter;
    private final SlugUtils slugUtils;

    /**
     * Возвращает список зарегистрированных шаблонов по идентификатору дефиниции
     *
     * @param entityDefId
     * @return
     */
    public Page<EntityTemplateDto> getEntityTemplatesByEntityDefId(UUID entityDefId, EntityTemplateFilterDto filter) {
        return entityTemplateRepository.findAll(
                    entityTemplateSpec(entityDefId, filter),
                    PageRequest.of(filter.getPageNumber(), filter.getPageSize()))
                .map(entityTemplateConverter::convertToDto);
    }

    /**
     * Возвращает список зарегистрированных шаблонов по коду дефиниции
     *
     * @param entityDefCode
     * @return
     */
    public Page<EntityTemplateDto> getEntityTemplatesByEntityDefCode(String entityDefCode, EntityTemplateFilterDto filter) {
        var entityDef = entityDefRepository.getEntityDefByCode(entityDefCode)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefCode));
        return getEntityTemplatesByEntityDefId(entityDef.getId(), filter);
    }

    public List<EntityTemplateDto> getEntityTemplatesByEntityId(UUID entityId) {
        return entityDefRepository.getEntityDefIdByEntityId(entityId).map(entityDefId ->
                entityTemplateRepository.findAll(entityTemplateSpec(entityDefId, null),
                                Sort.by(Sort.Direction.ASC, EntityDefGroup_.NAME)).stream()
                        .map(entityTemplateConverter::convertToDto).toList()).orElseThrow(() ->
                new EntityNotFoundException(
                        "Entity Def was not found by Entity Id",
                        entityId.toString()));
    }

    public EntityTemplateDto getEntityTemplateById(String entityDefCode, UUID entityTemplateId) {
        // TODO: можно добавить проверку да соответствие дефиниции
        return entityTemplateRepository.findById(entityTemplateId)
                .map(entityTemplateConverter::convertToDto).orElseThrow(() ->
                        new EntityNotFoundException(
                                "Entity Template was not found",
                                entityTemplateId.toString()));
    }

    public Map<String, Object> getEntityTemplateMapById(String entityDefCode, UUID entityTemplateId) {
        // TODO: можно добавить проверку на соответствие дефиниции
        return entityTemplateRepository.findById(entityTemplateId)
                .map(entityTemplateConverter::makeFieldMap).orElseThrow(() ->
                        new EntityNotFoundException(
                                "Entity Template was not found",
                                entityTemplateId.toString()));
    }

    public void deleteEntityTemplateById(String entityDefCode, UUID entityTemplateId) {
        // TODO: можно добавить проверку да соответствие дефиниции
        entityTemplateRepository.findById(entityTemplateId)
                .ifPresentOrElse(entityTemplateRepository::delete, () -> {
                    throw new EntityNotFoundException(
                            "Entity Template was not found",
                            entityDefId.toString());
                });
    }

    public Specification<EntityTemplate> entityTemplateSpec(@Nullable UUID entityDefId, EntityTemplateFilterDto filter) {
        return (root, query, builderSource) -> {
            var builder = (HibernateCriteriaBuilder) builderSource;

            // предикат по дефиниции
            var basePredicate = entityDefId != null
                    ? builder.equal(root.get(EntityTemplate_.entityDef).get(BaseEntity_.id), entityDefId)
                    : builder.and();

            if (filter == null) {
                return basePredicate;
            }

            if (CollectionUtils.isNotEmpty(filter.getStatuses())) {
                basePredicate = builder.and(
                        basePredicate, root.get(EntityTemplate_.status).in(filter.getStatuses()));
             }

            if (filter.getSearchText() != null) {
                basePredicate = builder.and(
                        basePredicate,
                        builder.like(builder.lower(root.get(EntityTemplate_.name)), "%" + filter.getSearchText().toLowerCase() + "%")
                );
            }

            if (filter.getCreatedAtFrom() != null) {
                basePredicate = builder.and(
                        basePredicate, builder.greaterThanOrEqualTo(root.get(EntityTemplate_.createdTs), filter.getCreatedAtFrom()));
            }

            if (filter.getCreatedAtTo() != null) {
                basePredicate = builder.and(
                        basePredicate, builder.lessThanOrEqualTo(root.get(EntityTemplate_.createdTs), filter.getCreatedAtTo()));
            }

            if (filter.getUpdatedAtFrom() != null) {
                basePredicate = builder.and(
                        basePredicate, builder.greaterThanOrEqualTo(root.get(EntityTemplate_.updatedTs), filter.getUpdatedAtFrom()));
            }

            if (filter.getUpdatedAtTo() != null) {
                basePredicate = builder.and(
                        basePredicate, builder.lessThanOrEqualTo(root.get(EntityTemplate_.updatedTs), filter.getUpdatedAtTo()));
            }

            if (filter.getFieldNameOrder() != null) {
                var fieldName = filter.getFieldNameOrder().getFieldName();

                if (filter.getFieldNameOrder().isDescending()) {
                    query.orderBy(builder.desc(root.get(fieldName)));
                } else {
                    query.orderBy(builder.asc(root.get(fieldName)));
                }
            }

            return basePredicate;
        };
    }

    @Transactional
    public EntityTemplateDto saveEntityTemplate(String entityDefCode, EntityTemplateDto entityTemplateDto) {
        if (entityTemplateDto.getCode() == null) {
            // код на форме не заполняется, построим его через slug
            entityTemplateDto.setCode(slugUtils.nameToSlug(entityTemplateDto.getName()));
        }
        return entityTemplateConverter.convertToDto(entityTemplateRepository
                .save(entityTemplateConverter.convertToModel(entityTemplateDto)));
    }
}
