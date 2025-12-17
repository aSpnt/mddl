package ru.softmachine.odyssey.backend.cms.provider.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NullPrecedence;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.softmachine.odyssey.backend.cms.converter.EntityConverter;
import ru.softmachine.odyssey.backend.cms.converter.context.EntityMappingContext;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseStringRef;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityPatchDto;
import ru.softmachine.odyssey.backend.cms.dto.EntitySeqDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.field.FieldDefDto;
import ru.softmachine.odyssey.backend.cms.dto.filter.EntityFilterDto;
import ru.softmachine.odyssey.backend.cms.dto.filter.FieldFilterBlockDto;
import ru.softmachine.odyssey.backend.cms.dto.filter.FieldFilterDto;
import ru.softmachine.odyssey.backend.cms.dto.filter.FieldOrderDto;
import ru.softmachine.odyssey.backend.cms.dto.filter.FilterGlobalOperator;
import ru.softmachine.odyssey.backend.cms.dto.filter.FilterOperator;
import ru.softmachine.odyssey.backend.cms.dto.filter.SearchFilterDto;
import ru.softmachine.odyssey.backend.cms.entity.Entity;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef_;
import ru.softmachine.odyssey.backend.cms.entity.Entity_;
import ru.softmachine.odyssey.backend.cms.entity.ExternalDictionaryValue_;
import ru.softmachine.odyssey.backend.cms.entity.FieldValue;
import ru.softmachine.odyssey.backend.cms.entity.FieldValue_;
import ru.softmachine.odyssey.backend.cms.dto.base.FieldType;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;
import ru.softmachine.odyssey.backend.cms.dto.field.FieldDefType;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef_;
import ru.softmachine.odyssey.backend.cms.exception.EntityNotFoundException;
import ru.softmachine.odyssey.backend.cms.hibernate.ArrayFunctionContributor;
import ru.softmachine.odyssey.backend.cms.hibernate.FtsFunctionContributor;
import ru.softmachine.odyssey.backend.cms.provider.EntityStorageProvider;
import ru.softmachine.odyssey.backend.cms.repository.EntityCommentRepository;
import ru.softmachine.odyssey.backend.cms.repository.EntityDefRepository;
import ru.softmachine.odyssey.backend.cms.repository.EntityRepository;
import ru.softmachine.odyssey.backend.cms.repository.FieldDefRepository;
import ru.softmachine.odyssey.backend.cms.repository.FieldValueRepository;
import ru.softmachine.odyssey.backend.cms.security.UserInfo;
import ru.softmachine.odyssey.backend.model.UprCmsSlugBaseEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static ru.softmachine.odyssey.backend.cms.hibernate.ArrayFunctionContributor.ARRAY_ANY;
import static ru.softmachine.odyssey.backend.cms.hibernate.ArrayFunctionContributor.ARRAY_OVERLAP;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class OwnEntityStorageProvider implements EntityStorageProvider {

    private final EntityRepository entityRepository;
    private final EntityConverter entityConverter;
    private final FieldDefRepository fieldDefRepository;
    private final FieldValueRepository fieldValueRepository;
    private final EntityCommentRepository entityCommentRepository;
    private final EntityDefRepository entityDefRepository;

    @Value("${app.language.default}")
    private String defaultLanguage;

    @Value("${app.loader.max-depth}")
    private Number maxDepth;

    /**
     * Метод для прикладного использования, оперирует мнемоническими кодами для задания
     * фильтрации и слагом для получения сущности.
     *
     * @param entityDefCode код типа сущности
     * @param entitySlug    слаг сущности
     * @return
     */
    @Override
    public Map<String, Object> getEntityByEntityDefAndSlug(
            String entityDefCode,
            String entitySlug
    ) {
        var entityOpt = entityRepository.findBySlugAndDefType(entitySlug, entityDefCode);
        if (entityOpt.isPresent()
                && entityOpt.get().getEntityDef().getCode().equals(entityDefCode)) {
            return entityConverter.mapFull(entityOpt.get());
        } else {
            throw new EntityNotFoundException("Entity not found", entitySlug);
        }
    }

    @Override
    public UprCmsSlugBaseEntity getBaseSlugEntityByEntityDefAndSlug(
            String entityDefCode,
            String entitySlug
    ) {
        var entityOpt = entityRepository.findBySlugAndDefType(entitySlug, entityDefCode);
        if (entityOpt.isPresent()
                && entityOpt.get().getEntityDef().getCode().equals(entityDefCode)) {
            return entityConverter.convertToBaseSlugEntity(entityOpt.get());
        } else {
            throw new EntityNotFoundException("Entity not found", entitySlug);
        }
    }

    /**
     * Метод для прикладного использования, оперирует мнемоническими кодами для задания
     * фильтрации и списком слагов для получения сущностей.
     *
     * @param entityDefCode код типа сущности
     * @param entitySlugs   слаги сущностей
     * @return
     */
    @Override
    public List<Map<String, Object>> getEntityByEntityDefAndSlugList(
            String entityDefCode,
            List<String> entitySlugs
    ) {

        var entities = entityRepository.getEntitiesBySlugsAndDefType(entitySlugs, entityDefCode);

        if (CollectionUtils.isEmpty(entities) || entities.size() != entitySlugs.size()) {
            var notFoundEntities = entitySlugs.stream()
                    .filter(slug -> !entities.stream()
                            .map(Entity::getSlug)
                            .toList()
                            .contains(slug))
                    .toList();
            log.warn("Entities by slugs not found: {}", notFoundEntities);
        }

        return entities.stream().map(entityConverter::mapShort).toList();
    }

    @Override
    public List<Map<String, Object>> getAllEntityByEntityDefAndIdList(String entityDefCode, List<String> entityIds) {
        var entities = entityRepository.getEntitiesByIdsAndDefType(entityIds, entityDefCode);

        if (CollectionUtils.isEmpty(entities) || entities.size() != entityIds.size()) {
            var notFoundEntities = entityIds.stream()
                    .filter(id -> !entities.stream()
                            .map(Entity::getId)
                            .map(UUID::toString)
                            .toList()
                            .contains(id))
                    .toList();
            log.warn("Entities by ids not found: {}", notFoundEntities);
        }

        return entities.stream().map(entityConverter::mapFull).toList();
    }

    @Override
    public List<UprCmsSlugBaseEntity> getSlugListByEntityDefAndIdList(String entityDefCode, List<String> entityIds) {
        var entities = entityRepository.getSlugListByEntityDefAndIdList(entityDefCode, entityIds);
        var slugBaseEntities = entities.stream()
                .map(entityConverter::convertToBaseSlugEntity)
                .toList();

        if (CollectionUtils.isEmpty(slugBaseEntities) || slugBaseEntities.size() != entityIds.size()) {
            var notFoundEntities = entityIds.stream()
                    .filter(slug -> !slugBaseEntities.stream()
                            .map(UprCmsSlugBaseEntity::getId)
                            .toList()
                            .contains(slug))
                    .toList();
            log.warn("Slugs by ids not found: {}", notFoundEntities);
        }

        return slugBaseEntities;
    }

    /**
     * Метод для прикладного использования, оперирует мнемоническими кодами для задания
     * фильтрации и идентификатором ля получения сущности.
     *
     * @param entityDefCode код типа сущности
     * @param entityId      идентификатор сущности
     * @return
     */
    @Override
    public Map<String, Object> getEntityByEntityDefCode(
            String entityDefCode,
            UUID entityId
    ) {
        var entityOpt = entityRepository.findById(entityId);
        if (entityOpt.isPresent()
                && entityOpt.get().getEntityDef().getCode().equals(entityDefCode)) {
            return entityConverter.mapFull(entityOpt.get());
        } else {
            throw new EntityNotFoundException("Entity not found", entityId.toString());
        }
    }

    /**
     * Метод для прикладного использования, оперирует мнемоническим кодам дефиниции для получения
     * ее синглтона.
     *
     * @param entityDefCode код типа сущности
     * @return
     */
    @Override
    public Map<String, Object> getSingletonEntityByEntityDefCode(
            String entityDefCode
    ) {
        var entityDef = entityDefRepository.getEntityDefByCode(entityDefCode).orElseThrow(
                () -> new EntityNotFoundException("Entity Def not found", entityDefCode)
        );
        return getSingletonEntityByEntityDefId(entityDef);
    }

    @Override
    public Map<String, Object> getSingletonEntityByEntityDefId(UUID entityDefId) {
        var entityDef = entityDefRepository.findById(entityDefId).orElseThrow(
                () -> new EntityNotFoundException("Entity Def not found", entityDefId.toString())
        );
        return getSingletonEntityByEntityDefId(entityDef);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    protected Map<String, Object> getSingletonEntityByEntityDefId(EntityDef entityDef) {
        var entitySingleton = entityDef.getSingletonEntity();
        if (entitySingleton != null) {
            return entityConverter.mapFull(entitySingleton);
        } else {
            throw new EntityNotFoundException("Entity Def Singleton not created", entityDef.getCode());
        }
    }

    /**
     * Оптимизирует загрузку ассоциаций
     *
     * @param entities
     * @param depth    ограничение глубины оптимизации
     */
    @Transactional(propagation = Propagation.REQUIRED)
    protected void batchLoadingEntities(List<Entity> entities, int depth) {
        if (entities.isEmpty() || depth <= 0) {
            return;
        }

        // расширение entity через entity graph
        entityRepository.loadEntitiesToContextByIds(
                entities.stream()
                        .map(Entity::getId)
                        .distinct()
                        .toList()
        );

        var fieldValueIds = entities.stream()
                .flatMap(e -> e.getValues().stream())
                .map(FieldValue::getId)
                .distinct()
                .toList();

        // пакетное расширение values через entity graph
        fieldValueRepository.loadFieldValueCollectionBatch(fieldValueIds);
        fieldValueRepository.loadFieldValueGeometryBatch(fieldValueIds);
        fieldValueRepository.loadFieldValueExternalBatch(fieldValueIds);
        fieldValueRepository.loadFieldValueFieldCollectionBatch(fieldValueIds);

        batchLoadingEntities(Stream.concat(
                        Stream.concat(
                                entities.stream()
                                        .flatMap(e -> e.getValues().stream())
                                        .map(FieldValue::getEntityValue)
                                        .filter(Objects::nonNull),
                                entities.stream()
                                        .flatMap(e -> e.getValues().stream())
                                        .map(FieldValue::getRefValue)
                                        .filter(Objects::nonNull)
                        ),
                        Stream.concat(
                                entities.stream()
                                        .flatMap(e -> e.getValues().stream())
                                        .flatMap(fv -> fv.getEntities().stream()),
                                entities.stream()
                                        .flatMap(e -> e.getValues().stream())
                                        .flatMap(fv -> fv.getRefValueCollection().stream())
                        )).toList(),
                depth - 1);
    }

    @Override
    public Page<EntityDto> getAllEntityByEntityDef(
            UUID entityDefId,
            EntityFilterDto filter
    ) {
        var entities = entityRepository.findAll(
                makeSpecification(entityDefId, filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())
        );

        // оптимизация загрузки
        batchLoadingEntities(entities.toList(), maxDepth.intValue()); // максимальная глубина 3

        return new PageImpl<>(entityConverter.convertToDtosWithContext(entities.toList(), new EntityMappingContext(false)),
                entities.getPageable(),
                entities.getTotalElements()
        );
    }

    /**
     * Метод для прикладного использования, оперирует мнемоническими кодами для задания
     * фильтрации.
     *
     * @param entityDefId код типа сущности
     * @param filter
     * @return
     */
    @Override
    public Page<Map<String, Object>> getAllEntityByEntityDefCode(
            UUID entityDefId,
            EntityFilterDto filter,
            Pageable pageable
    ) {
        var entities = entityRepository.findAll(makeSpecification(entityDefId, filter), pageable);
        // оптимизация загрузки
        batchLoadingEntities(entities.toList(), maxDepth.intValue()); // максимальная глубина 3

        return entities
                .map(entityConverter::mapShort);
    }


    /**
     * Метод используется только для снятия дампа и возвращает
     * все сущности дефиниции.
     *
     * @param entityDefId
     * @return
     */
    public List<EntityDto> getDumpEntityByEntityDef(
            UUID entityDefId
    ) {
        return entityRepository.findAll(makeSpecification(entityDefId, new EntityFilterDto())).stream()
                .map(entityConverter::convertToDto)
                .toList();
    }

    /**
     * Метод для поиска базовых ссылок на сущности по идентификатору дефиниции фильтру и пейджингу
     *
     * @param entityDefId код типа сущности
     * @param filter
     * @return
     */
    @Override
    public List<BaseRef> getAllEntityRefByEntityDefCode(
            UUID entityDefId,
            EntityFilterDto filter,
            Pageable pageable
    ) {
        Stream<Entity> entityStream;
        if (pageable == null) {
            entityStream = entityRepository.findAll(makeSpecification(entityDefId, filter)).stream();
        } else {
            entityStream = entityRepository.findAll(makeSpecification(entityDefId, filter), pageable).stream();
        }
        return entityStream.map(entityConverter::convertToBaseRef).toList();
    }

    @Override
    public EntityDto getEntityById(UUID entityDefId, String entityId) {
        return entityRepository.findById(UUID.fromString(entityId))
                .map((Entity e) -> entityConverter.convertToDtoWithContext(e, new EntityMappingContext(true)))
                .orElseThrow(() -> new EntityNotFoundException("Entity def was not found", entityId));
    }

    /**
     * Удаляет каскадно сущность по идентификатору
     *
     * @param entityId идентификатор сущности
     * @see FieldDefDto
     */
    @Override
    public void deleteEntityById(UUID entityDefId, String entityId) {
        entityRepository.findById(UUID.fromString(entityId))
                .ifPresentOrElse(entity -> {
                    entityCommentRepository.deleteByEntityId(entity.getId());
                    entityRepository.delete(entity);
                }, () -> {
                    throw new EntityNotFoundException(
                            "Entity was not found",
                            entityId);
                });
    }

    /**
     * Возвращает иерархическую (со вложенными сущностями) карту значений по идентификатору
     *
     * @param entityId идентификатор сущности
     * @return карта значений для сущности, где ключ - код поля сущности
     */
    @Override
    public Map<String, Object> getEntityMapById(UUID entityDefId, String entityId) {
        return entityConverter.makeFieldMap(
                entityRepository.findById(UUID.fromString(entityId))
                        .orElseThrow(() -> new EntityNotFoundException("Entity was not found", entityId)), true, true);
    }

    /**
     * Каскадно сохраняет сущность по полному представлению
     *
     * @param entityDto полное представление сущности
     * @return обновленную или созданную сущность по переданной DTO
     */
    @Override
    public EntityDto saveEntity(UUID entityDefId, EntityDto entityDto) {
        var entity = entityConverter.convertToModel(entityDto);

        // security
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserInfo userInfo) {
            if (entityDto.getId() == null) {
                // только при создании
                entity.setAuthor(userInfo.name());
                entity.setAuthorEmail(userInfo.email());
            }
        }

        var entitySaved = entityRepository.saveAndFlush(entity);

        return entityConverter.convertToDto(entitySaved);
    }

    /**
     * Позволяет частично сохранять значения сущности
     *
     * @param entityDto полное представление сущности
     * @return обновленную или созданную сущность по переданной DTO
     */
    @Override
    public EntityDto patchEntity(UUID entityDefId, EntityPatchDto entityDto) {
        var entity = entityConverter.patchEntityValuesWithDto(entityDto);
        var entitySaved = entityRepository.save(entity);

        return entityConverter.convertToDto(entitySaved);
    }

    @Override
    public void afterEntityChange(String entityId) {
        try {
            entityRepository.recalculateSearchVectors(UUID.fromString(entityId));
        } catch (Exception e) {
            // не считаем постэффекты критичными
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void setSequences(EntitySeqDto entitySeqDto) {
        entityRepository.updateSeq(UUID.fromString(entitySeqDto.getId()), entitySeqDto.getSeq());
    }


    /**
     * Строит предикат для блока фильтров по полям и рекурсивно внутренни блокам
     *
     * @param filters
     * @param filterBlock
     * @param op
     * @param builder
     * @param root
     * @param query
     * @return
     */
    private Predicate makePredicateByFilters(
            List<FieldFilterDto> filters,
            List<FieldFilterBlockDto> filterBlock,
            FilterGlobalOperator op,
            HibernateCriteriaBuilder builder,
            Root<Entity> root,
            CriteriaQuery<?> query
    ) {
        var subquery = filters.stream()
                .map(fieldFilter ->
                        makeSubqueryForFieldCondition(query, fieldFilter, builder))
                .reduce((subq1, subq2) -> op == FilterGlobalOperator.AND
                        ? builder.intersect(subq1, subq2)
                        : builder.union(subq1, subq2))
                .orElse(null);

        var fieldPredicate = subquery != null
                ? root.get(Entity_.id).in(subquery)
                : (op == FilterGlobalOperator.AND ? builder.and() : builder.or());

        var blockPredicate = filterBlock != null
                ? filterBlock.stream()
                .map(fieldFilterBlock -> makePredicateByFilters(
                        fieldFilterBlock.getFieldFilters(),
                        List.of(),
                        fieldFilterBlock.getOperator(),
                        builder, root, query))
                .reduce((p1, p2) -> op == FilterGlobalOperator.AND
                        ? builder.and(p1, p2)
                        : builder.or(p1, p2))
                .orElse(op == FilterGlobalOperator.AND ? builder.and() : builder.or())
                : (op == FilterGlobalOperator.AND ? builder.and() : builder.or());

        return op == FilterGlobalOperator.AND
                ? builder.and(fieldPredicate, blockPredicate)
                : builder.or(fieldPredicate, blockPredicate);
    }

    /**
     * Формирует спецификацию запроса на основании переданного фильтра
     *
     * @param entityDefId ссылка на определение типа сущности
     * @param filter      фильтр
     * @return спецификация построенная на основе фильтра
     */
    private Specification<Entity> makeSpecification(UUID entityDefId, EntityFilterDto filter) {
        return (root, query, builderSource) -> {
            var builder = (HibernateCriteriaBuilder) builderSource;

            // предикат по дефиниции
            var basePredicate = builder.equal(root.get(Entity_.entityDef).get(EntityDef_.id), entityDefId);

            // условный предикат
            var predicate = (filter.getGlobalOperator() == null || filter.getGlobalOperator() == FilterGlobalOperator.AND)
                    ? builder.and()
                    : builder.or();

            // фильтрация только по активным
            if (filter.isActiveOnly()) {
                predicate = builder.and(predicate, builder.equal(root.get(Entity_.active), true));
            }

            // фильтрация по фиксированным (прямое отображение на колонку)
            if (CollectionUtils.isNotEmpty(filter.getFixedFieldFilters())) {
                var predicates = filter.getFixedFieldFilters().stream()
                        .map(fieldFilter ->
                                makeSubqueryForFixedFieldCondition(root, fieldFilter, builderSource))
                        .toList();
                for (var filterPredicate : predicates) {
                    predicate = builder.and(predicate, filterPredicate);
                }
            }

            // полнотекстовый поиск
            if (filter.getSearchFilter() != null
                    && filter.getSearchFilter().getFtsSearch() != null) {
                var ftsPredicate = makeFtsPredicateForValue(root, builder, filter.getSearchFilter().getFtsSearch());
                if (filter.getGlobalOperator() == null || filter.getGlobalOperator() == FilterGlobalOperator.AND) {
                    predicate = builder.and(predicate, ftsPredicate);
                } else {
                    predicate = builder.or(predicate, ftsPredicate);
                }
            }

            // текстовый поиск
            if (filter.getSearchFilter() != null
                    && CollectionUtils.isNotEmpty(filter.getSearchFilter().getFields())
                    && filter.getSearchFilter().getSearch() != null) {
                var subquery = makeSearchPredicate(filter.getSearchFilter(), query, builder);
                if (filter.getGlobalOperator() == null || filter.getGlobalOperator() == FilterGlobalOperator.AND) {
                    predicate = builder.and(predicate, root.get(Entity_.id).in(subquery));
                } else {
                    predicate = builder.or(predicate, root.get(Entity_.id).in(subquery));
                }
            }

            // фильтрация по динамическим полям
            if (CollectionUtils.isNotEmpty(filter.getFieldFilters())) {
                var filterPredicate = makePredicateByFilters(
                        filter.getFieldFilters(),
                        filter.getFieldFilterBlocks(),
                        filter.getGlobalOperator(),
                        builder, root, query);
                // в зависимости от типа глобального оператора
                if (filter.getGlobalOperator() == null || filter.getGlobalOperator() == FilterGlobalOperator.AND) {
                    predicate = builder.and(predicate, filterPredicate);
                } else {
                    predicate = builder.or(predicate, filterPredicate);
                }
            }

            // при использовании полнотекстового поиска сортировка автоматически выполняется по рангу
            if (filter.getSearchFilter() != null && filter.getSearchFilter().getFtsSearch() != null) {
                query.orderBy(builder.desc(builder.function(
                        FtsFunctionContributor.FTS_RANK,
                        Double.class,
                        root.get(Entity_.ftsVector),
                        builder.function(
                                FtsFunctionContributor.TO_QUERY,
                                String.class,
                                builder.literal(defaultLanguage),
                                builder.literal(prepareFtsSearchText(filter.getSearchFilter().getFtsSearch()))
                        )
                )));
            } else {
                if (filter.getFieldFilters() != null) {
                    // для поиска по пересечению сортировка формируется автоматически
                    // по убыванию количества пересечений тегов. При этом это может быть не единственная
                    // сортировка, в отличие от полнотекстового поиска.
                    var intersectField = filter.getFieldFilters().stream()
                            .filter(f -> f.getOperator().equals(FilterOperator.INTERSECT)).findFirst();

                    if (intersectField.isPresent()) {
                        query.orderBy(builder.desc(
                                makeSubqueryForIntersect(
                                        root,
                                        query,
                                        intersectField.get().getFieldDef().getId(),
                                        intersectField.get().getArrayText(),
                                        builder
                                )
                        ));
                    }
                }

                // сортировка
                if (filter.getFieldOrder() != null) {
                    if (filter.getFieldOrder().isDescending()) {
                        query.orderBy(builder.desc(
                                        makeSubqueryForOrderFieldCondition(root, query, filter.getFieldOrder(), builder))
                                .nullPrecedence(NullPrecedence.LAST));
                    } else {
                        query.orderBy(builder.asc(
                                makeSubqueryForOrderFieldCondition(root, query, filter.getFieldOrder(), builder)));
                    }
                }

                // сортировка по фиксированным полям
                if (filter.getFixedFieldOrder() != null && filter.getFixedFieldOrder().getFieldDef() != null) {
                    var fieldDef = fieldDefRepository.findByIdAndType(
                                    filter.getFixedFieldOrder().getFieldDef().getId(),
                                    FieldDefType.FIXED
                            )
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "Field def was not found",
                                    filter.getFixedFieldOrder().getFieldDef().getId().toString())
                            );
                    if (filter.getFixedFieldOrder().isDescending()) {
                        query.orderBy(builder.desc(root.get(fieldDef.getCode())).nullPrecedence(NullPrecedence.LAST));
                    } else {
                        query.orderBy(builder.asc(root.get(fieldDef.getCode())).nullPrecedence(NullPrecedence.LAST));
                    }
                }

                // базовая сортировка по seq по возрастанию
                if (filter.getFieldOrder() == null && filter.getFixedFieldOrder() == null) {
                    query.orderBy(builder.asc(root.get(Entity_.seq)));
                    query.orderBy(builder.asc(root.get(Entity_.id)));
                }
            }

            return builder.and(basePredicate, predicate);
        };
    }

    /**
     * Формирует предикат для стандартного текстового поиска (по нескольким полям)
     *
     * @param search  непустой фильтр
     * @param builder
     * @return предикат построенный как дизъюнкция условий текстового поиска по каждому полю
     */
    private Subquery<UUID> makeSearchPredicate(
            SearchFilterDto search,
            CriteriaQuery<?> globalQuery,
            CriteriaBuilder builder
    ) {
        Subquery<UUID> query = globalQuery.subquery(UUID.class);
        Root<Entity> root = query.from(Entity.class);

        query.select(root.get(Entity_.id));

        var fieldValueJoin = root.join(Entity_.values);
        var fieldDefJoin = fieldValueJoin.join(FieldValue_.fieldDef);

        return query.where(
                builder.or(
                        search.getFields().stream()
                                .map(fieldDef ->
                                        builder.and(
                                                builder.equal(fieldDefJoin.get(FieldDef_.id), fieldDef.getId()),
                                                builder.like(builder.lower(fieldValueJoin.get(FieldValue_.textValue)),
                                                        "%" + search.getSearch().toLowerCase() + "%")
                                        )
                                )
                                .toList()
                                .toArray(new Predicate[0])
                )
        );
    }

    /**
     * Возвращает подзапрос для отдельного условия по полю
     *
     * @param fieldFilter условие по полю
     * @param builder
     * @return
     */
    private Subquery<UUID> makeSubqueryForFieldCondition(
            CriteriaQuery<?> globalQuery,
            FieldFilterDto fieldFilter,
            CriteriaBuilder builder
    ) {
        Subquery<UUID> query = globalQuery.subquery(UUID.class);
        Root<Entity> root = query.from(Entity.class);

        query.select(root.get(Entity_.id));

        var fieldValueJoin = root.join(Entity_.values);
        var fieldDefJoin = fieldValueJoin.join(FieldValue_.fieldDef);

        var fieldDef = fieldDefRepository.findById(fieldFilter.getFieldDef().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Field def was not found",
                        fieldFilter.getFieldDef().getId().toString())
                );

        return query.where(builder.and(
                builder.equal(fieldDefJoin.get(FieldDef_.id), fieldDef.getId()),
                makePredicateForValue(fieldFilter, fieldDef, fieldValueJoin, builder))
        );
    }

    /**
     * Метод подготавливает полнотекстовый запрос на основе исходного текста запроса.
     * На текущий момент запрос модифицируется для поиска по вхождению токена запроса в токен вектора по
     * которому производится поиск.
     *
     * @param text исходный текст запроса
     * @return полнотекстовый запрос
     */
    private String prepareFtsSearchText(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        return String.join(":* | ",
                text.trim().replaceAll(" +", " ").split(" ")) + ":*";
    }

    /**
     * Строит полнотекстовый предикат по подготовленной колонке с
     * конкатенацией текстовых значений по полям
     *
     * @param root
     * @param builder
     * @param search  строка для полнотекстового поиска
     * @return
     */
    private Predicate makeFtsPredicateForValue(Root<Entity> root, CriteriaBuilder builder, String search) {
        return builder.equal(builder.function(
                FtsFunctionContributor.FTS,
                Boolean.class,
                builder.function(FtsFunctionContributor.TO_QUERY,
                        String.class,
                        builder.literal(defaultLanguage),
                        builder.literal(prepareFtsSearchText(search))
                ),
                root.get(Entity_.ftsVector)
        ), Boolean.TRUE);
    }

    /**
     * Строит фильтрующий предикат на основе фильтра по полю,
     * с учетом типа поля для фиксированных полей сущности
     *
     * @param filter   фильтр по полю
     * @param fieldDef определения типа поля
     * @param root     корень запроса сущность
     * @param builder
     * @return
     */
    private Predicate makePredicateForFixedColumn(
            FieldFilterDto filter,
            FieldDef fieldDef,
            Root<Entity> root,
            CriteriaBuilder builder
    ) {
        if (List.of(
                FieldType.STRING,
                FieldType.TEXT,
                FieldType.HTML,
                FieldType.JSON
        ).contains(fieldDef.getType())) {
            if (filter.getOperator() == FilterOperator.EQUAL) {
                return builder.equal(root.get(fieldDef.getCode()), filter.getTextValue());
            }
            if (filter.getOperator() == FilterOperator.LIKE) {
                return builder.like(root.get(fieldDef.getCode()),
                        "%" + filter.getTextValue() + "%");
            }
        }
        if (fieldDef.getType() == FieldType.COLOR) {
            if (filter.getOperator() == FilterOperator.EQUAL) {
                return builder.equal(root.get(fieldDef.getCode()), filter.getTextValue());
            }
        }
        // дата
        if (fieldDef.getType() == FieldType.DATE) {
            if (filter.getOperator() == FilterOperator.EQUAL) {
                return builder.equal(root.get(fieldDef.getCode()), filter.getDateValue());
            }
            if (filter.getOperator() == FilterOperator.GT) {
                return builder.greaterThan(root.get(fieldDef.getCode()), filter.getDateValue());
            }
            if (filter.getOperator() == FilterOperator.GTE) {
                return builder.greaterThanOrEqualTo(root.get(fieldDef.getCode()), filter.getDateValue());
            }
            if (filter.getOperator() == FilterOperator.LT) {
                return builder.lessThan(root.get(fieldDef.getCode()), filter.getDateValue());
            }
            if (filter.getOperator() == FilterOperator.LTE) {
                return builder.lessThanOrEqualTo(root.get(fieldDef.getCode()), filter.getDateValue());
            }
        }
        // Date time
        if (fieldDef.getType() == FieldType.DATETIME) {
            if (filter.getOperator() == FilterOperator.EQUAL) {
                return builder.equal(root.get(fieldDef.getCode()), filter.getDateTimeValue());
            }
            if (filter.getOperator() == FilterOperator.GT) {
                return builder.greaterThan(root.get(fieldDef.getCode()), filter.getDateTimeValue());
            }
            if (filter.getOperator() == FilterOperator.GTE) {
                return builder.greaterThanOrEqualTo(root.get(fieldDef.getCode()), filter.getDateTimeValue());
            }
            if (filter.getOperator() == FilterOperator.LT) {
                return builder.lessThan(root.get(fieldDef.getCode()), filter.getDateTimeValue());
            }
            if (filter.getOperator() == FilterOperator.LTE) {
                return builder.lessThanOrEqualTo(root.get(fieldDef.getCode()), filter.getDateTimeValue());
            }
        }
        // целочисленные значения
        if (fieldDef.getType() == FieldType.INT) {
            if (filter.getOperator() == FilterOperator.EQUAL) {
                return builder.equal(root.get(fieldDef.getCode()), filter.getIntValue());
            }
            if (filter.getOperator() == FilterOperator.GT) {
                return builder.greaterThan(root.get(fieldDef.getCode()), filter.getIntValue());
            }
            if (filter.getOperator() == FilterOperator.GTE) {
                return builder.greaterThanOrEqualTo(root.get(fieldDef.getCode()), filter.getIntValue());
            }
            if (filter.getOperator() == FilterOperator.LT) {
                return builder.lessThan(root.get(fieldDef.getCode()), filter.getIntValue());
            }
            if (filter.getOperator() == FilterOperator.LTE) {
                return builder.lessThanOrEqualTo(root.get(fieldDef.getCode()), filter.getIntValue());
            }
        }
        // значения с плавающей запятой
        if (fieldDef.getType() == FieldType.DOUBLE) {
            if (filter.getOperator() == FilterOperator.EQUAL) {
                return builder.equal(root.get(fieldDef.getCode()), filter.getDoubleValue());
            }
            if (filter.getOperator() == FilterOperator.GT) {
                return builder.greaterThan(root.get(fieldDef.getCode()), filter.getDoubleValue());
            }
            if (filter.getOperator() == FilterOperator.GTE) {
                return builder.greaterThanOrEqualTo(root.get(fieldDef.getCode()), filter.getDoubleValue());
            }
            if (filter.getOperator() == FilterOperator.LT) {
                return builder.lessThan(root.get(fieldDef.getCode()), filter.getDoubleValue());
            }
            if (filter.getOperator() == FilterOperator.LTE) {
                return builder.lessThanOrEqualTo(root.get(fieldDef.getCode()), filter.getDoubleValue());
            }
        }

        // если предикат построить не удалось
        return builder.and();
    }

    /**
     * Отдельный метод для построения предиката даты (думаю вынести в отдельные классы или бины)
     *
     * @param filter
     * @param fieldValueJoin
     * @param builder
     * @return
     */
    private Predicate makeDatePredicate(
            FieldFilterDto filter,
            Join<Entity, FieldValue> fieldValueJoin,
            CriteriaBuilder builder
    ) {
        if (filter.getOperator() == FilterOperator.EQUAL) {
            return builder.equal(fieldValueJoin.get(FieldValue_.dateValue), filter.getDateValue());
        }
        if (filter.getOperator() == FilterOperator.GT) {
            return builder.greaterThan(fieldValueJoin.get(FieldValue_.dateValue), filter.getDateValue());
        }
        if (filter.getOperator() == FilterOperator.GTE) {
            return builder.greaterThanOrEqualTo(fieldValueJoin.get(FieldValue_.dateValue), filter.getDateValue());
        }
        if (filter.getOperator() == FilterOperator.LT) {
            return builder.lessThan(fieldValueJoin.get(FieldValue_.dateValue), filter.getDateValue());
        }
        if (filter.getOperator() == FilterOperator.LTE) {
            return builder.lessThanOrEqualTo(fieldValueJoin.get(FieldValue_.dateValue), filter.getDateValue());
        }
        return null;
    }

    /**
     * Строит фильтрующий предикат на основе фильтра по полю,
     * с учетом типа поля
     *
     * @param filter         фильтр по полю
     * @param fieldDef       определения типа поля
     * @param fieldValueJoin фильтр строится в рамках контекста запроса, выраженного и переданного через Join
     * @param builder
     * @return
     */
    private Predicate makePredicateForValue(
            FieldFilterDto filter,
            FieldDef fieldDef,
            Join<Entity, FieldValue> fieldValueJoin,
            CriteriaBuilder builder
    ) {
        if (List.of(
                FieldType.STRING,
                FieldType.TEXT,
                FieldType.SLUG,
                FieldType.HTML,
                FieldType.JSON
        ).contains(fieldDef.getType())) {
            if (filter.getOperator() == FilterOperator.EQUAL) {
                return builder.equal(fieldValueJoin.get(FieldValue_.textValue), filter.getTextValue());
            }
            if (filter.getOperator() == FilterOperator.LIKE) {
                return builder.like(builder.lower(fieldValueJoin.get(FieldValue_.textValue)),
                        "%" + filter.getTextValue().toLowerCase() + "%");
            }
            if (filter.getOperator() == FilterOperator.IN) {
                return fieldValueJoin.get(FieldValue_.textValue).in(filter.getArrayText());
            }
        }
        if (fieldDef.getType() == FieldType.COLOR) {
            if (filter.getOperator() == FilterOperator.EQUAL) {
                return builder.equal(fieldValueJoin.get(FieldValue_.textValue), filter.getTextValue());
            }
        }
        // теги
        if (fieldDef.getType() == FieldType.TAGS) {
            if (filter.getOperator() == FilterOperator.IN) {
                return builder.equal(
                        builder.function(ARRAY_ANY, Boolean.class, builder.literal(filter.getTextValue()),
                                fieldValueJoin.get(FieldValue_.arrayText)),
                        Boolean.TRUE);
            }
            if (filter.getOperator() == FilterOperator.INTERSECT) {
                return builder.equal(
                        builder.function(ARRAY_OVERLAP, Boolean.class,
                                ((HibernateCriteriaBuilder) builder).arrayLiteral(filter.getArrayText().toArray()),
                                fieldValueJoin.get(FieldValue_.arrayText)),
                        Boolean.TRUE);
            }
        }
        // логическое значение
        if (fieldDef.getType() == FieldType.BOOLEAN) {
            if (filter.getOperator() == FilterOperator.EQUAL) {
                // допускается отсутствие значения в фильтре, считаем, что подразумевается true
                return builder.equal(fieldValueJoin.get(FieldValue_.booleanValue),
                        Optional.ofNullable(filter.getBooleanValue()).orElse(true));
            }
        }
        // дата
        if (fieldDef.getType() == FieldType.DATE) {
            var p = makeDatePredicate(filter, fieldValueJoin, builder);
            if (p != null) {
                return p;
            }
        }
        // дата
        if (fieldDef.getType() == FieldType.DATETIME) {
            if (filter.getOperator() == FilterOperator.EQUAL) {
                return builder.equal(fieldValueJoin.get(FieldValue_.datetimeValue), filter.getDateTimeValue());
            }
            if (filter.getOperator() == FilterOperator.GT) {
                return builder.greaterThan(fieldValueJoin.get(FieldValue_.datetimeValue), filter.getDateTimeValue());
            }
            if (filter.getOperator() == FilterOperator.GTE) {
                return builder.greaterThanOrEqualTo(fieldValueJoin.get(FieldValue_.datetimeValue), filter.getDateTimeValue());
            }
            if (filter.getOperator() == FilterOperator.LT) {
                return builder.lessThan(fieldValueJoin.get(FieldValue_.datetimeValue), filter.getDateTimeValue());
            }
            if (filter.getOperator() == FilterOperator.LTE) {
                return builder.lessThanOrEqualTo(fieldValueJoin.get(FieldValue_.datetimeValue), filter.getDateTimeValue());
            }
        }
        // целочисленные значения
        if (fieldDef.getType() == FieldType.INT) {
            if (filter.getOperator() == FilterOperator.EQUAL) {
                return builder.equal(fieldValueJoin.get(FieldValue_.intValue), filter.getIntValue());
            }
            if (filter.getOperator() == FilterOperator.GT) {
                return builder.greaterThan(fieldValueJoin.get(FieldValue_.intValue), filter.getIntValue());
            }
            if (filter.getOperator() == FilterOperator.GTE) {
                return builder.greaterThanOrEqualTo(fieldValueJoin.get(FieldValue_.intValue), filter.getIntValue());
            }
            if (filter.getOperator() == FilterOperator.LT) {
                return builder.lessThan(fieldValueJoin.get(FieldValue_.intValue), filter.getIntValue());
            }
            if (filter.getOperator() == FilterOperator.LTE) {
                return builder.lessThanOrEqualTo(fieldValueJoin.get(FieldValue_.intValue), filter.getIntValue());
            }
        }
        // значения с плавающей запятой
        if (fieldDef.getType() == FieldType.DOUBLE) {
            if (filter.getOperator() == FilterOperator.EQUAL) {
                return builder.equal(fieldValueJoin.get(FieldValue_.doubleValue), filter.getDoubleValue());
            }
            if (filter.getOperator() == FilterOperator.GT) {
                return builder.greaterThan(fieldValueJoin.get(FieldValue_.doubleValue), filter.getDoubleValue());
            }
            if (filter.getOperator() == FilterOperator.GTE) {
                return builder.greaterThanOrEqualTo(fieldValueJoin.get(FieldValue_.doubleValue), filter.getDoubleValue());
            }
            if (filter.getOperator() == FilterOperator.LT) {
                return builder.lessThan(fieldValueJoin.get(FieldValue_.doubleValue), filter.getDoubleValue());
            }
            if (filter.getOperator() == FilterOperator.LTE) {
                return builder.lessThanOrEqualTo(fieldValueJoin.get(FieldValue_.doubleValue), filter.getDoubleValue());
            }
        }
        // внутренние словари (и статус как частный случай)
        if (List.of(
                FieldType.DICTIONARY,
                FieldType.STATUS
        ).contains(fieldDef.getType())) {
            var dictJoin = fieldDef.isMultiple() ?
                    fieldValueJoin.join(FieldValue_.refValueCollection) :
                    fieldValueJoin.join(FieldValue_.refValue);
            if (filter.getOperator() == FilterOperator.EQUAL) {
                return builder.equal(dictJoin.get(Entity_.id), filter.getRefValue().getId());
            }
            if (filter.getOperator() == FilterOperator.IN) {
                return dictJoin.get(Entity_.id).in(filter.getDictionaryValues().stream().map(BaseRef::getId).toList());
            }
        }
        // внешние справочники
        if (Objects.equals(FieldType.DICTIONARY_EXTERNAL, fieldDef.getType())) {
            var extDictJoin = fieldValueJoin.join(FieldValue_.externalValues);
            if (filter.getOperator() == FilterOperator.EQUAL && filter.getExternalRefValue() != null) {
                return builder.equal(extDictJoin.get(ExternalDictionaryValue_.id), filter.getExternalRefValue().getId());
            }
            if (filter.getOperator() == FilterOperator.IN && filter.getExternalDictionaryValues() != null) {
                // TODO: тут некорректно, по логике должно быть вхождение всех, а по факту INTERSECT
                return extDictJoin.get(ExternalDictionaryValue_.id).in(
                        filter.getExternalDictionaryValues().stream().map(BaseStringRef::getId).toList());
            }
            if (filter.getOperator() == FilterOperator.INTERSECT && filter.getExternalDictionaryValues() != null) {
                return extDictJoin.get(ExternalDictionaryValue_.id).in(
                        filter.getExternalDictionaryValues().stream().map(BaseStringRef::getId).toList());
            }
        }

        log.warn("Unsupported filter for type: {} and operator: {}",
                fieldDef.getType(), filter.getOperator().toString());
        // если предикат построить не удалось
        return builder.and();
    }

    /**
     * Определяет выборку значения в зависимости от типа поля
     *
     * @param fieldDef       определения типа поля
     * @param fieldValueJoin фильтр строится в рамках контекста запроса, выраженного и переданного через Join
     * @return
     */
    private Expression<?> getValueForOrder(
            FieldDef fieldDef,
            Join<Entity, FieldValue> fieldValueJoin,
            CriteriaBuilder builder
    ) {
        if (List.of(
                FieldType.STRING,
                FieldType.TEXT
        ).contains(fieldDef.getType())) {
            return builder.lower(fieldValueJoin.get(FieldValue_.textValue));
        }
        if (List.of(
                FieldType.HTML,
                FieldType.COLOR,
                FieldType.JSON
        ).contains(fieldDef.getType())) {
            return fieldValueJoin.get(FieldValue_.textValue);
        }
        // дата
        if (fieldDef.getType() == FieldType.DATE) {
            return fieldValueJoin.get(FieldValue_.dateValue);
        }
        // дата и время
        if (fieldDef.getType() == FieldType.DATETIME) {
            return fieldValueJoin.get(FieldValue_.datetimeValue);
        }
        // целочисленные значения
        if (fieldDef.getType() == FieldType.INT) {
            return fieldValueJoin.get(FieldValue_.intValue);
        }
        // значения с плавающей запятой
        if (fieldDef.getType() == FieldType.DOUBLE) {
            return fieldValueJoin.get(FieldValue_.doubleValue);
        }

        return fieldValueJoin.get(FieldValue_.textValue);
    }

    /**
     * Возвращает подзапрос для отдельного условия по полю
     *
     * @param fieldFilter условие по полю
     * @param builder
     * @return
     */
    private Predicate makeSubqueryForFixedFieldCondition(
            Root<Entity> root,
            FieldFilterDto fieldFilter,
            CriteriaBuilder builder
    ) {
        var fieldDef = fieldDefRepository.findByIdAndType(fieldFilter.getFieldDef().getId(), FieldDefType.FIXED)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Field def was not found",
                        fieldFilter.getFieldDef().getId().toString())
                );
        return makePredicateForFixedColumn(fieldFilter, fieldDef, root, builder);
    }


    /**
     * Возвращает подзапрос для сортировки
     *
     * @param orderDto условие сортировки
     * @param builder
     * @return
     */
    private Subquery<Object> makeSubqueryForOrderFieldCondition(
            Root<Entity> globalRoot,
            CriteriaQuery<?> globalQuery,
            FieldOrderDto orderDto,
            CriteriaBuilder builder
    ) {
        Subquery<Object> query = globalQuery.subquery(Object.class);

        Root<Entity> root = query.from(Entity.class);
        var fieldValueJoin = root.join(Entity_.values);
        var fieldDefJoin = fieldValueJoin.join(FieldValue_.fieldDef);

        var fieldDef = fieldDefRepository.findById(orderDto.getFieldDef().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Field def was not found",
                        orderDto.getFieldDef().getId().toString())
                );

        query = query.where(
                builder.and(
                        builder.equal(fieldDefJoin.get(FieldDef_.id), fieldDef.getId()),
                        builder.equal(globalRoot.get(Entity_.id), root.get(Entity_.id))
                ));

        return query.select((Expression<Object>) getValueForOrder(fieldDef, fieldValueJoin, builder));
    }

    /**
     * Возвращает подзапрос для сортировки по количеству пересечений
     * TODO: вероятно можно объединить с методом makeSubqueryForOrderFieldCondition задавая дополнительную логику извлечения значения
     *
     * @param fieldDefId идентификатор дефиниции поля по которому строится запрос
     * @param textArray  массив строк из запроса
     * @param builder
     * @return
     */
    private Subquery<Integer> makeSubqueryForIntersect(
            Root<Entity> globalRoot,
            CriteriaQuery<?> globalQuery,
            UUID fieldDefId,
            List<String> textArray,
            CriteriaBuilder builder
    ) {
        Subquery<Integer> query = globalQuery.subquery(Integer.class);

        Root<Entity> root = query.from(Entity.class);
        var fieldValueJoin = root.join(Entity_.values);
        var fieldDefJoin = fieldValueJoin.join(FieldValue_.fieldDef);

        query = query.where(
                builder.and(
                        builder.equal(fieldDefJoin.get(FieldDef_.id), fieldDefId),
                        builder.equal(globalRoot.get(Entity_.id), root.get(Entity_.id))
                ));

        return query.select(
                builder.function(
                        ArrayFunctionContributor.ARRAY_OVERLAP_LENGTH,
                        Integer.class,
                        ((HibernateCriteriaBuilder) builder).arrayLiteral(textArray.toArray()),
                        fieldValueJoin.get(FieldValue_.arrayText)
                )
        );
    }
}
