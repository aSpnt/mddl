package ru.softmachine.odyssey.backend.cms.provider;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityPatchDto;
import ru.softmachine.odyssey.backend.cms.dto.EntitySeqDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.filter.EntityFilterDto;
import ru.softmachine.odyssey.backend.model.UprCmsSlugBaseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface EntityStorageProvider {

    Map<String, Object> getEntityByEntityDefAndSlug(
            String entityDefCode,
            String entitySlug
    );

    UprCmsSlugBaseEntity getBaseSlugEntityByEntityDefAndSlug(
            String entityDefCode,
            String entitySlug
    );

    List<Map<String, Object>> getEntityByEntityDefAndSlugList(
            String entityDefCode,
            List<String> entitySlugs
    );

    List<Map<String, Object>> getAllEntityByEntityDefAndIdList(
            String entityDefCode,
            List<String> entityIds
    );

    List<UprCmsSlugBaseEntity> getSlugListByEntityDefAndIdList(
            String entityDefCode,
            List<String> entityIds
    );

    Map<String, Object> getEntityByEntityDefCode(
            String entityDefCode,
            UUID entityId
    );

    Map<String, Object> getSingletonEntityByEntityDefCode(
            String entityDefCode
    );

    Map<String, Object> getSingletonEntityByEntityDefId(
            UUID entityDefId
    );

    Page<EntityDto> getAllEntityByEntityDef(
            UUID entityDefId,
            EntityFilterDto filter
    );

    List<EntityDto> getDumpEntityByEntityDef(
            UUID entityDefId
    );

    Page<Map<String, Object>> getAllEntityByEntityDefCode(
            UUID entityDefId,
            EntityFilterDto filter,
            Pageable pageable
    );

    List<BaseRef> getAllEntityRefByEntityDefCode(
            UUID entityDefId,
            EntityFilterDto filter,
            Pageable pageable
    );

    EntityDto getEntityById(UUID entityDefId, String entityId);

    void deleteEntityById(UUID entityDefId, String entityId);

    Map<String, Object> getEntityMapById(UUID entityDefId, String entityId);

    EntityDto saveEntity(UUID entityDefId, EntityDto entityDto);

    EntityDto patchEntity(UUID entityDefId, EntityPatchDto entityDto);

    /**
     * Постэффекты после изменения или сохранения сущности
     * @param entityId
     */
    void afterEntityChange(String entityId);

    void setSequences(EntitySeqDto entitySeqDto);
}
