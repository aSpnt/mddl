package ru.softmachine.odyssey.backend.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.softmachine.odyssey.backend.cms.entity.EntityTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntityTemplateRepository extends JpaRepository<EntityTemplate, UUID>,
        JpaSpecificationExecutor<EntityTemplate> {

    @Query("""
            select et from EntityTemplate et
            join et.entityDef ed
            where ed.id = :entityDefId
            order by et.createdTs desc
            """)
    List<EntityTemplate> getEntityTemplatesByEntityDefId(UUID entityDefId);

    @Query("select et from EntityTemplate et where et.code = :entityTemplateCode")
    Optional<EntityTemplate> findByCode(String entityTemplateCode);

    @Query("""
            select et from EntityTemplate et
                join et.entityDef ed
            where
                ed.id = :entityDefId and
                et.id = :entityTemplateId
            """)
    Optional<EntityTemplate> getByIdAndEntityDefId(UUID entityDefId, UUID entityTemplateId);
}





