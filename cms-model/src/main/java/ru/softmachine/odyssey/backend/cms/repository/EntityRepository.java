package ru.softmachine.odyssey.backend.cms.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.softmachine.odyssey.backend.cms.entity.Entity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntityRepository extends JpaRepository<Entity, UUID>,
        JpaSpecificationExecutor<Entity> {

    @Query("""
            select e from Entity e 
            join e.entityDef ed
            where ed.id = :entityDefId
            order by e.createdTs desc
            """)
    List<Entity> getEntitiesByEntityDefId(UUID entityDefId);

    @EntityGraph(attributePaths = {"values", "values.fieldDef"})
    @Query("select e from Entity e where e.id in :ids")
    List<Entity> loadEntitiesToContextByIds(List<UUID> ids);

    @Query(value = "select calculate_entity_fts(:entityId)", nativeQuery = true)
    void recalculateSearchVectors(UUID entityId);

    @Query("""
            select e from Entity e
            join e.entityDef ed
            where e.slug = :entitySlug and ed.code = :entityDefCode
            """)
    Optional<Entity> findBySlugAndDefType(String entitySlug, String entityDefCode);

    @Query("""
           select e
           from Entity e
           join e.entityDef ed
           where e.slug in :slugs and ed.code = :entityDefCode
           """)
    List<Entity> getEntitiesBySlugsAndDefType(List<String> slugs, String entityDefCode);

    @Query("""
           select e
           from Entity e
           join e.entityDef ed
           where e.id in :ids and ed.code = :entityDefCode
           """)
    List<Entity> getEntitiesByIdsAndDefType(List<String> ids, String entityDefCode);

    @Modifying
    @Query("""
        update Entity e
        set e.seq = :seq
        where e.id = :entityId
    """)
    void updateSeq(UUID entityId, int seq);

    /**
     * Спорно: Для неудаляемых сущностей обновление
     * флагов и таймстампов не применяется
     */
    @Modifying
    @Query("""
        update Entity e
        set e.lastStatusChangeTs = :ts,
            e.active = :active,
            e.updatedTs = :ts
        where e.id = :entityId and e.deleteLock = false
    """)
    void updateLastStatusChange(UUID entityId, boolean active, ZonedDateTime ts);

    /**
     * Спорно: Для неудаляемых сущностей обновление
     * флагов и таймстампов не применяется
     */
    @Modifying
    @Query("""
        update Entity e
        set e.lastStatusChangeTs = NULL,
            e.active = :active,
            e.updatedTs = :ts
        where e.id = :entityId and e.deleteLock = false
    """)
    void resetLastStatusChange(UUID entityId, boolean active, ZonedDateTime ts);

    @Query("""
            select e from Entity e
            join e.entityDef ed
            where e.id in :ids and ed.code = :entityDefCode
    """)
    List<Entity> getSlugListByEntityDefAndIdList(String entityDefCode, List<String> ids);

    @Query("""
            select count(e) from Entity e
            where e.entityDef.id = :entityDefId
    """)
    Integer getTotalCount(UUID entityDefId);
}





