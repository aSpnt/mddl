package com.aspnt.mddl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.aspnt.mddl.entity.entitydef.EntityDef;
import com.aspnt.mddl.entity.entitydef.EntityDefProviderProj;

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
                new com.aspnt.mddl.entity.entitydef.EntityDefProviderProj(ed.id, ed.providerType)
                from EntityDef ed
            where ed.code = :entityDefCode
            """)
    Optional<EntityDefProviderProj> getEntityDefIdByEntityDefCode(String entityDefCode);

    @Query("""
            select
                new com.aspnt.mddl.entity.entitydef.EntityDefProviderProj(ed.id, ed.providerType)
                from EntityDef ed
            where ed.id = :entityDefId
            """)
    Optional<EntityDefProviderProj> getEntityDefIdByEntityDefCode(UUID entityDefId);
}
