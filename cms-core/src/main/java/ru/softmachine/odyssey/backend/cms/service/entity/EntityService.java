package ru.softmachine.odyssey.backend.cms.service.entity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.softmachine.odyssey.backend.cms.converter.EntityCommentConverter;
import ru.softmachine.odyssey.backend.cms.converter.EntityConverter;
import ru.softmachine.odyssey.backend.cms.dto.EntityCommentDto;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;
import ru.softmachine.odyssey.backend.cms.dto.EntitySeqListDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityPatchDto;
import ru.softmachine.odyssey.backend.cms.dto.field.FieldDefDto;
import ru.softmachine.odyssey.backend.cms.dto.filter.EntityFilterDto;
import ru.softmachine.odyssey.backend.cms.repository.EntityCommentRepository;
import ru.softmachine.odyssey.backend.cms.exception.EntityNotFoundException;
import ru.softmachine.odyssey.backend.cms.dto.provider.ProviderType;
import ru.softmachine.odyssey.backend.cms.provider.StorageProviderManager;
import ru.softmachine.odyssey.backend.cms.repository.EntityDefRepository;
import ru.softmachine.odyssey.backend.model.UprCmsSlugBaseEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EntityService {

    private final EntityDefRepository entityDefRepository;
    private final StorageProviderManager storageProviderManager;
    private final EntityConverter entityConverter;
    private final EntityCommentConverter entityCommentConverter;
    private final EntityCommentRepository entityCommentRepository;
    private final EntityServiceBase entityServiceBase;
    private final EmptyEntityService emptyEntityService;

    /**
     * Надстройка над методом обновления сущности по прикладному представлению
     *
     * @param entityDefCode
     * @param entityId
     * @param entityMap
     * @return
     */
    public Map<String, Object> updateEntityByMapAndId(String entityDefCode, String entityId, Map<String, Object> entityMap) {
        if (entityId != null) {
            entityMap.put("id", entityId); // приоритет у идентификатора переданного через Path
        }
        return saveEntityByMap(entityDefCode, entityMap);
    }

    /**
     * Часть прикладного АПИ, выполняет преобразования прикладного представления
     * для работы с базовым слоем
     *
     * @param entityDefCode
     * @param entityMap
     * @return
     */
    public Map<String, Object> saveEntityByMap(String entityDefCode, Map<String, Object> entityMap) {
        var entityDef = entityDefRepository.getEntityDefByCode(entityDefCode).orElseThrow(() ->
                new EntityNotFoundException("Entity def was not found", entityDefCode));
        var entityDto = entityConverter.deserializeEntityByMap(entityDef, entityMap, this::getEntityById);
        return entityConverter.makeFieldMap(
                entityServiceBase.saveEntity(entityDto, entityDef.getId())
        );
    }

    /**
     * Метод для прикладного использования, оперирует мнемоническими кодами для задания
     * фильтрации и слагом для получения сущности.
     *
     * @param entityDefCode код типа сущности
     * @param entitySlug    слаг сущности
     * @return
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getEntityByEntityDefAndSlug(
            String entityDefCode,
            String entitySlug
    ) {
        return storageProviderManager.getProviderByEntityDef(ProviderType.DEFAULT)
                .getEntityByEntityDefAndSlug(entityDefCode, entitySlug);
    }

    /**
     * Метод для прикладного использования, возвращает краткое представление сущности по слагу.
     *
     * @param entityDefCode код типа сущности
     * @param entitySlug    слаг сущности
     * @return
     */
    @Transactional(readOnly = true)
    public UprCmsSlugBaseEntity getBaseSlugEntityByEntityDefAndSlug(
            String entityDefCode,
            String entitySlug
    ) {
        return storageProviderManager.getProviderByEntityDef(ProviderType.DEFAULT)
                .getBaseSlugEntityByEntityDefAndSlug(entityDefCode, entitySlug);
    }

    /**
     * Метод для прикладного использования, оперирует мнемоническими кодами для задания
     * фильтрации и списком слагов для получения сущностей.
     *
     * @param entityDefCode код типа сущности
     * @param entitySlugs   слаги сущностей
     * @return
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getEntityByEntityDefAndSlugList(
            String entityDefCode,
            List<String> entitySlugs
    ) {
        return storageProviderManager.getProviderByEntityDef(ProviderType.DEFAULT)
                .getEntityByEntityDefAndSlugList(entityDefCode, entitySlugs);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getAllEntityByEntityDefAndIdList(
            String entityDefCode,
            List<String> entityIds
    ) {
        return storageProviderManager.getProviderByEntityDef(ProviderType.DEFAULT)
                .getAllEntityByEntityDefAndIdList(entityDefCode, entityIds);
    }

    @Transactional(readOnly = true)
    public List<UprCmsSlugBaseEntity> getSlugListByIdList(
            String entityDefCode,
            List<String> entityIds
    ) {
        return storageProviderManager.getProviderByEntityDef(ProviderType.DEFAULT)
                .getSlugListByEntityDefAndIdList(entityDefCode, entityIds);
    }


    /**
     * Метод для прикладного использования, оперирует мнемоническими кодами для задания
     * фильтрации и идентификатором ля получения сущности.
     *
     * @param entityDefCode код типа сущности
     * @param entityId      идентификатор сущности
     * @return
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getEntityByEntityDefCode(
            String entityDefCode,
            UUID entityId
    ) {
        return storageProviderManager.getProviderByEntityDef(ProviderType.DEFAULT)
                .getEntityByEntityDefCode(entityDefCode, entityId);
    }

    /**
     * Метод для прикладного использования, оперирует мнемоническим кодом дефиниции для получения синглтона.
     *
     * @param entityDefCode код типа сущности
     * @return
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getSingletonEntityByEntityDefCode(
            String entityDefCode
    ) {
        var entityDefProviderProj = entityDefRepository.getEntityDefIdByEntityDefCode(entityDefCode).orElseThrow(() ->
                new EntityNotFoundException("Entity Def was not found", entityDefCode));
        return entityServiceBase.getSingletonEntityByEntityDefCode(entityDefProviderProj.id());
    }

    /**
     * TODO: Нужно сравнить эффективность и распространить оптимизацию
     *
     * @param entityDefId
     * @param filter
     * @return
     */
    @Transactional(readOnly = true)
    public Page<EntityDto> getAllEntityByEntityDef(
            UUID entityDefId,
            EntityFilterDto filter
    ) {
        var entityDefProviderProj = entityDefRepository.getEntityDefIdByEntityDefCode(entityDefId).orElseThrow(() ->
                new EntityNotFoundException("Entity Def was not found", entityDefId.toString()));
        return storageProviderManager.getProviderByEntityDef(entityDefProviderProj.type())
                .getAllEntityByEntityDef(entityDefProviderProj.id(), filter);
    }

    /**
     * TODO: Нужно сравнить эффективность и распространить оптимизацию
     *
     * @param entityDefCode
     * @param filter
     * @return
     */
    @Transactional(readOnly = true)
    public Page<EntityDto> getAllEntityByEntityDefCode(
            String entityDefCode,
            EntityFilterDto filter
    ) {
        var entityDefProviderProj = entityDefRepository.getEntityDefIdByEntityDefCode(entityDefCode)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefCode));
        return storageProviderManager.getProviderByEntityDef(entityDefProviderProj.type())
                .getAllEntityByEntityDef(entityDefProviderProj.id(), filter);
    }

    /**
     * Возвращает все сущности дефиниции для дампа, нужно пользоваться очень осторожно.
     *
     * @param entityDefCode
     * @return
     */
    @Transactional(readOnly = true)
    public List<EntityDto> getDumpEntitiesByEntityDefCode(
            String entityDefCode
    ) {
        var entityDef = entityDefRepository.getEntityDefByCode(entityDefCode)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefCode));
        return entityServiceBase.getDumpEntitiesByEntityDefCode(entityDef.getId());
    }

    /**
     * Метод для прикладного использования, оперирует мнемоническими кодами для задания
     * фильтрации.
     *
     * @param entityDefCode код типа сущности
     * @param filter
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Map<String, Object>> getAllEntityByEntityDefCode(
            String entityDefCode,
            EntityFilterDto filter,
            Pageable pageable
    ) {
        var entityDef = entityDefRepository.getEntityDefByCode(entityDefCode)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefCode));
        return entityServiceBase.getAllEntityByEntityDef(entityDef.getId(), filter, pageable);
    }

    /**
     * Метод для прикладного использования, оперирует мнемоническими кодами для задания
     * фильтрации.
     *
     * @param entityDefCode код типа сущности
     * @param filter
     * @return
     */
    @Transactional(readOnly = true)
    public List<BaseRef> getAllEntityRefByEntityDefCode(
            String entityDefCode,
            EntityFilterDto filter,
            Pageable pageable
    ) {
        var entityDef = entityDefRepository.getEntityDefByCode(entityDefCode)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefCode));
        return entityServiceBase.getAllEntityRefByEntityDefCode(entityDef.getId(), filter, pageable);
    }

    /**
     * Возвращает представление с разбивкой по значениям и ссылками на определение полей
     * сущности по идентификатору
     *
     * @param entityId идентификатор сущности
     * @return полное представление сущности
     * @see FieldDefDto
     */
    @Transactional(readOnly = true)
    public EntityDto getEntityById(UUID entityDefId, String entityId) {
        var entityDef = entityDefRepository.findById(entityDefId)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefId.toString()));
        return storageProviderManager.getProviderByEntityDef(entityDef.getProviderType())
                .getEntityById(entityDefId, entityId);
    }

    /**
     * Возвращает представление по коду дефиниции с разбивкой по значениям и ссылками на определение полей
     * сущности по идентификатору
     *
     * @param entityId идентификатор сущности
     * @return полное представление сущности
     * @see FieldDefDto
     */
    @Transactional(readOnly = true)
    public EntityDto getEntityByDefCodeById(String entityDefCode, String entityId) {
        var entityDef = entityDefRepository.getEntityDefByCode(entityDefCode)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefCode));
        return storageProviderManager.getProviderByEntityDef(entityDef.getProviderType())
                .getEntityById(entityDef.getId(), entityId);
    }

    /**
     * Возвращает иерархическую (со вложенными сущностями) карту значений по идентификатору
     *
     * @param entityId идентификатор сущности
     * @return карта значений для сущности, где ключ - код поля сущности
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getEntityMapById(UUID entityDefId, String entityId) {
        var entityDef = entityDefRepository.findById(entityDefId)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefId.toString()));
        return storageProviderManager.getProviderByEntityDef(entityDef.getProviderType())
                .getEntityMapById(entityDefId, entityId);
    }

    /**
     * Возвращает иерархическую (со вложенными сущностями) карту значений по идентификатору
     *
     * @param entityId идентификатор сущности
     * @return карта значений для сущности, где ключ - код поля сущности
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getEntityMapByDefCodeById(String entityDefCode, String entityId) {
        var entityDef = entityDefRepository.getEntityDefByCode(entityDefCode)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefCode));
        return storageProviderManager.getProviderByEntityDef(entityDef.getProviderType())
                .getEntityMapById(entityDef.getId(), entityId);
    }

    /**
     * Возвращает entity по карте значений
     *
     * @param entityDefId идентификатор сущности
     * @return карта значений для сущности, где ключ - код поля сущности
     */
    @Transactional(readOnly = true)
    public EntityDto getEntityByMap(UUID entityDefId, Map<String, Object> entityMap) {
        var entityDef = entityDefRepository.findById(entityDefId)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefId.toString()));
        return entityConverter.deserializeEntityByMap(entityDef, entityMap, this::getEntityById);
    }

    /**
     * Возвращает entity по карте значений
     *
     * @param entityDefCode код сущности
     * @return карта значений для сущности, где ключ - код поля сущности
     */
    @Transactional(readOnly = true)
    public EntityDto getEntityByMap(String entityDefCode, Map<String, Object> entityMap) {
        var entityDef = entityDefRepository.getEntityDefByCode(entityDefCode)
                .orElseThrow(() ->
                        new EntityNotFoundException("Entity Def was not found", entityDefCode));
        return entityConverter.deserializeEntityByMap(entityDef, entityMap, this::getEntityById);
    }

    /**
     * Метод позволяет установить для списка Entity номера их последовательности
     * TODO: по смыслу тут не хватает кода или идентификатора дефиниции
     *
     * @param seqListDto
     */
    public void setSequences(EntitySeqListDto seqListDto) {
        seqListDto.getSeqList().forEach(seqDto ->
                storageProviderManager.getProviderByEntityDef(ProviderType.DEFAULT)
                        .setSequences(seqDto));
    }

    /**
     * Комментарии загружаются отедльным запросом по идентификатору сущности
     *
     * @param entityId идентификатор суности
     * @return
     */
    public List<EntityCommentDto> getCommentsByEntityId(UUID entityId) {
        return entityCommentRepository.findByEntityId(entityId).stream()
                .map(entityCommentConverter::convertToDto)
                .toList();
    }

    public EntityDto saveEntity(EntityDto entityDto, @Nullable String entityDefCode) {
        var entityDef = entityDefRepository.getEntityDefByCode(entityDefCode)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefCode));
        return entityServiceBase.saveEntity(entityDto, entityDef.getId());
    }

    public EntityDto patchEntity(EntityPatchDto entityDto) {
        return entityServiceBase.patchEntity(entityDto);
    }

    public void deleteEntityByDefCodeAndId(String entityDefCode, String entityId) {
        var entityDef = entityDefRepository.getEntityDefByCode(entityDefCode)
                .orElseThrow(() -> new EntityNotFoundException("Entity Def was not found", entityDefCode));
        entityServiceBase.deleteEntityById(entityDef.getId(), entityId);
    }

    public void deleteEntityById(UUID entityDefId, String entityId) {
        entityServiceBase.deleteEntityById(entityDefId, entityId);
    }

    public void updateLastTsStatusChange(UUID entityId, boolean active) {
        entityServiceBase.updateLastTsStatusChange(entityId, active);
    }

    public void resetLastTsStatusChange(UUID entityId, boolean active) {
        entityServiceBase.resetLastTsStatusChange(entityId, active);
    }

    @Transactional(readOnly = true)
    public EntityDto getEmptyEntityByDefCode(
            Map<String, Object> context,
            String entityDefCode,
            @Nullable UUID entityTemplateId
    ) {
        return emptyEntityService.getEmptyEntityByDefCode(context, entityDefCode, entityTemplateId);
    }

    @Transactional(readOnly = true)
    public EntityDto getEmptyEntity(
            Map<String, Object> context,
            UUID entityDefId,
            @Nullable UUID entityTemplateId
    ) {
        return emptyEntityService.getEmptyEntity(context, entityDefId, entityTemplateId);
    }
}
