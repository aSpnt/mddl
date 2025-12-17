package ru.softmachine.odyssey.backend.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDefProviderProj;

import java.util.Optional;
import java.util.UUID;

public interface EntityDefRepository extends JpaRepository<EntityDef, UUID>,
        JpaSpecificationExecutor<EntityDef> {

    @Query("""
            select ed from EntityDef ed
            where ed.code = :entityDefCode
            """)
    Optional<EntityDef> getEntityDefByCode(String entityDefCode);

    @Query("""
            select ed.id from Entity e
                join e.entityDef ed
            where e.id = :entityId
            """)
    Optional<UUID> getEntityDefIdByEntityId(UUID entityId);

    @Query("""
            select
                new ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDefProviderProj(ed.id, ed.providerType)
                from EntityDef ed
            where ed.code = :entityDefCode
            """)
    Optional<EntityDefProviderProj> getEntityDefIdByEntityDefCode(String entityDefCode);

    @Query("""
            select
                new ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDefProviderProj(ed.id, ed.providerType)
                from EntityDef ed
            where ed.id = :entityDefId
            """)
    Optional<EntityDefProviderProj> getEntityDefIdByEntityDefCode(UUID entityDefId);
}
