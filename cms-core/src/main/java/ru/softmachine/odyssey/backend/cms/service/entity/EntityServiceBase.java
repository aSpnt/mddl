package ru.softmachine.odyssey.backend.cms.service.entity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.softmachine.odyssey.backend.cms.converter.EntityCommentConverter;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityPatchDto;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityWithCommentDto;
import ru.softmachine.odyssey.backend.cms.dto.field.FieldDefDto;
import ru.softmachine.odyssey.backend.cms.dto.filter.EntityFilterDto;
import ru.softmachine.odyssey.backend.cms.dto.base.FieldType;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;
import ru.softmachine.odyssey.backend.cms.exception.EntityNotFoundException;
import ru.softmachine.odyssey.backend.cms.exception.EntityReferenceException;
import ru.softmachine.odyssey.backend.cms.exception.ValidationException;
import ru.softmachine.odyssey.backend.cms.dto.provider.ProviderType;
import ru.softmachine.odyssey.backend.cms.provider.StorageProviderManager;
import ru.softmachine.odyssey.backend.cms.repository.EntityCommentRepository;
import ru.softmachine.odyssey.backend.cms.repository.EntityDefRepository;
import ru.softmachine.odyssey.backend.cms.repository.EntityRepository;
import ru.softmachine.odyssey.backend.cms.repository.FieldValueRepository;
import ru.softmachine.odyssey.backend.cms.security.UserInfo;
import ru.softmachine.odyssey.backend.cms.utils.EntityDefUtils;
import ru.softmachine.odyssey.backend.cms.utils.SlugUtils;
import ru.softmachine.odyssey.backend.cms.validation.ValidationManager;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static ru.softmachine.odyssey.backend.cms.config.CacheConfig.ENTITY_DEF_CACHE_RESOLVER;
import static ru.softmachine.odyssey.backend.cms.config.CacheConfig.SEARCH_ENTITY_COLLECTION_CACHE_NAME;
import static ru.softmachine.odyssey.backend.cms.config.CacheConfig.SINGLETON_ENTITY_DEF_CACHE_NAME;

/**
 * Представляет базовый слой для работы с сущностями, CRUD
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EntityServiceBase {

    private final EntityDefRepository entityDefRepository;
    private final SlugUtils slugUtils;
    private final EntityDefUtils entityDefUtils;
    private final ValidationManager validationManager;
    private final StorageProviderManager storageProviderManager;
    private final EntityCommentConverter entityCommentConverter;
    private final EntityCommentRepository entityCommentRepository;
    private final EntityRepository entityRepository;
    private final FieldValueRepository fieldValueRepository;

    @Value("${app.slug.separator}")
    private String slugSeparator;

    /**
     * Метод для прикладного использования, оперирует мнемоническими кодами для задания
     * фильтрации.
     *
     * @param entityDefId идентификатор дефиниции сущности
     * @param filter
     * @return
     */
    @Cacheable(cacheResolver = ENTITY_DEF_CACHE_RESOLVER)
    public Page<Map<String, Object>> getAllEntityByEntityDef(
            UUID entityDefId,
            EntityFilterDto filter,
            Pageable pageable
    ) {
        var entityDef = entityDefRepository.findById(entityDefId)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefId.toString()));
        return storageProviderManager.getProviderByEntityDef(entityDef.getProviderType())
                .getAllEntityByEntityDefCode(entityDef.getId(), filter, pageable);
    }

    /**
     * Запрос базовых ссылок с фильтром и пейджингом.
     *
     * @param entityDefId
     * @param filter
     * @param pageable
     * @return
     */
    public List<BaseRef> getAllEntityRefByEntityDefCode(
            UUID entityDefId,
            EntityFilterDto filter,
            Pageable pageable
    ) {
        var entityDef = entityDefRepository.findById(entityDefId)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefId.toString()));
        return storageProviderManager.getProviderByEntityDef(entityDef.getProviderType())
                .getAllEntityRefByEntityDefCode(entityDef.getId(), filter, pageable);
    }

    /**
     * Возвращает все сущности дефиниции для дампа, нужно пользоваться очень осторожно.
     *
     * @param entityDefId
     * @return
     */
    public List<EntityDto> getDumpEntitiesByEntityDefCode(
            UUID entityDefId
    ) {
        var entityDef = entityDefRepository.findById(entityDefId)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefId.toString()));
        return storageProviderManager.getProviderByEntityDef(entityDef.getProviderType())
                .getDumpEntityByEntityDef(entityDef.getId());
    }

    /**
     * Метод для прикладного использования, оперирует мнемоническим кодом дефиниции для получения синглтона.
     *
     * @param entityDefId id типа сущности
     * @return
     */
    @Cacheable(
            cacheNames = {SINGLETON_ENTITY_DEF_CACHE_NAME},
            key = "#entityDefId",
            unless = "#result == null"
    )
    @Transactional(readOnly = true)
    public Map<String, Object> getSingletonEntityByEntityDefCode(
            UUID entityDefId
    ) {
        return storageProviderManager.getProviderByEntityDef(ProviderType.DEFAULT)
                .getSingletonEntityByEntityDefId(entityDefId);
    }

    /**
     * Каскадно сохраняет сущность по полному представлению
     *
     * @param entityDto полное представление сущности
     * @return обновленную или созданную сущность по переданной DTO
     */
    @Caching(evict = {
            @CacheEvict(
                    value = SEARCH_ENTITY_COLLECTION_CACHE_NAME,
                    cacheResolver = ENTITY_DEF_CACHE_RESOLVER,
                    allEntries = true),
            @CacheEvict(
                    cacheNames = {SINGLETON_ENTITY_DEF_CACHE_NAME},
                    key = "#entityDefId"
            )
    })
    public EntityDto saveEntity(EntityDto entityDto, @Nullable UUID entityDefId) {
        var entityDef = entityDefId != null
                ? entityDefRepository.findById(entityDefId)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefId.toString()))
                : entityDefRepository.findById(entityDto.getEntityDef().getId())
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDto.getEntityDef().getId().toString()));

        // обработка автоматически заполняемых полей
        var processedValues = processFields(
                entityDefUtils.getAllFieldDefs(entityDef.getContainer()).toList(),
                entityDto.getValues(),
                entityDto
        );
        entityDto.setValues(processedValues);

        // проверка валидации
        var violations = validationManager.validate(entityDto);
        if (!violations.isEmpty()) {
            violations.forEach(violation ->
                    log.error(violation.getMessage()));
            throw new ValidationException("Ошибки при валидации сущности", violations);
        }

        var resultEntity = storageProviderManager.getProviderByEntityDef(entityDef.getProviderType())
                .saveEntity(entityDef.getId(), entityDto);

        // постэффекты (например пересчет индекса)
        storageProviderManager.getProviderByEntityDef(entityDef.getProviderType())
                .afterEntityChange(resultEntity.getId());

        // обработка комментария
        if (entityDef.getProviderType() == ProviderType.DEFAULT
                && entityDto instanceof EntityWithCommentDto
                && ((EntityWithCommentDto) entityDto).getComment() != null) {
            try {
                var commentDto = ((EntityWithCommentDto) entityDto).getComment();
                ((EntityWithCommentDto) entityDto).getComment().setEntity(new BaseRef().setId(UUID.fromString(resultEntity.getId())));
                if (SecurityContextHolder.getContext().getAuthentication() != null
                        && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserInfo userInfo) {
                    commentDto.setAuthor(userInfo.name());
                    commentDto.setAuthorEmail(userInfo.email());
                }
                entityCommentRepository.save(
                        entityCommentConverter.convertToModel(commentDto));
            } catch (Exception e) {
                log.error("Не удалось записать комментарий для сущности " + resultEntity.getId(), e.getMessage());
            }
        }
        return resultEntity;
    }

    /**
     * Обновляет дату и время изменения статуса
     *
     * @param entityId идентификатор сущности
     */
    @Transactional
    public void updateLastTsStatusChange(UUID entityId, boolean active) {
        entityRepository.updateLastStatusChange(entityId, active, ZonedDateTime.now());
    }

    /**
     * Сбрасывает дату и время изменения статуса
     *
     * @param entityId идентификатор сущности
     */
    @Transactional
    public void resetLastTsStatusChange(UUID entityId, boolean active) {
        entityRepository.resetLastStatusChange(entityId, active, ZonedDateTime.now());
    }

    /**
     * Удаляет каскадно сущность по идентификатору
     *
     * @param entityId идентификатор сущности
     * @see FieldDefDto
     */
    /**
     * Удаляет каскадно сущность по идентификатору и коду дефиниции
     *
     * @param entityId идентификатор сущности
     * @see FieldDefDto
     */
    @Caching(evict = {
            @CacheEvict(
                    value = SEARCH_ENTITY_COLLECTION_CACHE_NAME,
                    cacheResolver = ENTITY_DEF_CACHE_RESOLVER,
                    allEntries = true),
            @CacheEvict(
                    cacheNames = {SINGLETON_ENTITY_DEF_CACHE_NAME},
                    key = "#entityDefId"
            )
    })
    public void deleteEntityById(UUID entityDefId, String entityId) {
        var entityDef = entityDefRepository.findById(entityDefId)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefId.toString()));
        try {
            storageProviderManager.getProviderByEntityDef(entityDef.getProviderType())
                    .deleteEntityById(entityDefId, entityId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityReferenceException("Cannot delete entity that has reference", entityId, e.getCause());
        }
    }

    /**
     * Позволяет частично сохранять значения сущности
     *
     * @param entityDto полное представление сущности
     * @return обновленную или созданную сущность по переданной DTO
     */
    public EntityDto patchEntity(EntityPatchDto entityDto) {
        var entityDef = entityDefRepository.findById(entityDto.getEntityDef().getId()).orElseThrow(() ->
                new EntityNotFoundException("Entity Def was not found", entityDto.getEntityDef().getId().toString()));
        // обработка автоматически заполняемых полей
        var processedValues = processFields(
                entityDefUtils.getAllFieldDefs(entityDef.getContainer()).toList(),
                entityDto.getValues(),
                entityDto
        );
        entityDto.setValues(processedValues);

        // проверка валидации
        var resultEntity = storageProviderManager.getProviderByEntityDef(entityDef.getProviderType())
                .patchEntity(entityDef.getId(), entityDto);

        storageProviderManager.getProviderByEntityDef(entityDef.getProviderType())
                .afterEntityChange(resultEntity.getId());

        // обработка комментария
        if (entityDef.getProviderType() == ProviderType.DEFAULT && entityDto.getComment() != null) {
            try {
                entityDto.getComment().setEntity(new BaseRef().setId(UUID.fromString(resultEntity.getId())));
                if (SecurityContextHolder.getContext().getAuthentication() != null
                        && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserInfo userInfo) {
                    entityDto.getComment().setAuthor(userInfo.name());
                    entityDto.getComment().setAuthorEmail(userInfo.email());
                } else {
                    entityDto.getComment().setAuthor(null);
                    entityDto.getComment().setAuthorEmail(null);
                }
                entityCommentRepository.save(
                        entityCommentConverter.convertToModel(
                                entityDto.getComment()));
            } catch (Exception e) {
                log.error("Не удалось записать комментарий для сущности " + resultEntity.getId(), e.getMessage());
            }
        }

        return resultEntity;
    }

    /**
     * Стадия обработки генерируемых значений полей (например SLUG)
     *
     * @param fieldDefs
     * @param values
     * @param entity
     * @return
     */
    private List<FieldValueDto> processFields(
            List<FieldDef> fieldDefs,
            List<FieldValueDto> values,
            EntityDto entity
    ) {
        var processedValues = new ArrayList<>(values);

        fieldDefs.forEach(fieldDef -> {
            // SLUG является генерируемым полем
            if (fieldDef.getType() == FieldType.SLUG) {
                var baseValue = fieldDefs.stream()
                        .filter(FieldDef::isForSlugGenerator)
                        .map(fieldDefForSlug -> values.stream()
                                .filter(value -> value.getFieldDef().getId().equals(fieldDefForSlug.getId()))
                                // для генерации ищем не только первый попавшийся флаг, но и отдаем предпочтение
                                // заполненным значениям TODO: эту логику нужно детерминировать конечно
                                .filter(value -> StringUtils.isNotBlank(value.getTextValue()))
                                .findFirst()
                                .orElse(null))
                        .filter(Objects::nonNull)
                        .findFirst();
                if (baseValue.isPresent()) {
                    // сгенерированное базовое значение слага
                    var genValueBase = slugUtils.nameToSlug(baseValue.get().getTextValue());
                    var slugIndex = fieldValueRepository.getNextSlugIndex(fieldDef.getId(), genValueBase, slugSeparator);
                    var genValue = slugIndex > 0 ? genValueBase + slugSeparator + slugIndex : genValueBase;

                    var existingValue = values.stream()
                            .filter(value -> value.getFieldDef().getId().equals(fieldDef.getId()))
                            .findFirst();
                    if (existingValue.isPresent()) {
                        if (StringUtils.isBlank(existingValue.get().getTextValue())) {
                            // TODO: нужно ориентироваьтся на slugLock
                            existingValue.get().setTextValue(genValue);
                            entity.setSlug(genValue);
                        } else {
                            // слаг уже генерировался и это ручное изменение
                            var manualSlug = slugUtils.nameToSlug(existingValue.get().getTextValue());
                            entity.setSlug(manualSlug);
                            existingValue.get().setTextValue(manualSlug);
                        }
                    } else {
                        // FIXME: вероятно нужно убрать тип поля и оставить флаг поля на основе которого строить слаг
                        entity.setSlug(genValue);

                        var fieldDefDto = new FieldDefDto();
                        fieldDefDto.setId(fieldDef.getId());
                        processedValues.add(new FieldValueDto()
                                .setFieldDef(fieldDefDto)
                                .setTextValue(genValue)
                        );
                    }
                }
            }
        });

        return processedValues;
    }
}
