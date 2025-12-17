package ru.softmachine.odyssey.backend.cms.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.softmachine.odyssey.backend.cms.entity.FieldValue;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public interface FieldValueRepository extends JpaRepository<FieldValue, UUID>,
        JpaSpecificationExecutor<FieldValue> {

    @Query(value = """
                select max(i) + 1
                from (select cast((regexp_matches(fv.text_value, :value || :separator || '(\\d+)', 'g'))[1] as integer) as i
                    from meta.field_value fv
                    where fv.field_def_id = :fieldDefId
                union
                select cast(0 as integer) as i
                    from meta.field_value fv
                    where fv.text_value = :value and fv.field_def_id = :fieldDefId
                union
                select cast(-1 as integer) as i) as counts
            """, nativeQuery = true)
    int getNextSlugIndex(UUID fieldDefId, String value, String separator);

    /**
     * Проверяет уникальность занчения поля в рамках коллекции родительской сущности
     *
     * @param fieldDefId       поле для которого проверяется уникальность
     * @param parentFieldDefId поле родительской сущности в рамках которой проверяется уникальность
     * @param fieldValueId     (может быть null) идентификатор значения для исключения из проверки (кроме самомго себя)
     * @param textValue        значеие для проверки
     * @return
     */
    @Query(value = """
                select exists(fv) from meta.field_value fv
                            join meta.entity e on fv.entity_id = e.id
                            join meta.ref_value_collection rvc on rvc.entity_id = e.id
                where fv.field_def_id = :fieldDefId and
                      rvc.field_value_id = :parentFieldDefId and
                      ((array [ :fieldValueId ]) = '{null}' or fv.id <> :fieldValueId) and
                      fv.text_value = :textValue
            """, nativeQuery = true)
    boolean checkUniqueInCollection(UUID fieldDefId, UUID parentFieldDefId, @Nullable UUID fieldValueId, String textValue);

    // TODO: нужно убрать отсюда COLLATE и создавать БД с корректным сопоставлением
    @Query(value = """
                    with tags as (
                        select unnest(array_text) as tag from meta.field_value fv where field_def_id = :fieldDefId
                    )
                    select distinct tag COLLATE "ru-RU-x-icu" from tags where tag ilike '%' || :search || '%' 
                        order by tag COLLATE "ru-RU-x-icu" asc limit :limit
            """, nativeQuery = true)
    List<String> findTagsByField(UUID fieldDefId, String search, int limit);

    /**
     * Проверяет уникальность занчения поля глобально
     *
     * @param fieldDefId   поле для которого проверяется уникальность
     * @param fieldValueId (может быть null) идентификатор значения для исключения из проверки (кроме самомго себя)
     * @param textValue    значеие для проверки
     * @return
     */
    @Query(value = """
                select count(fv) > 0 from meta.field_value fv
                where fv.field_def_id = :fieldDefId and
                      ((array [ :fieldValueId ]) = '{null}' or fv.id <> :fieldValueId) and
                      fv.text_value = :textValue
            """, nativeQuery = true)
    boolean checkUnique(UUID fieldDefId, @Nullable UUID fieldValueId, String textValue);

    @EntityGraph(attributePaths = {"refValue", "refValueCollection"})
    @Query("select fv from FieldValue fv where fv.id in :ids")
    List<FieldValue> loadFieldValueCollectionBatch(List<UUID> ids);

    @EntityGraph(attributePaths = {"entities", "entityValue"})
    @Query("select fv from FieldValue fv where fv.id in :ids")
    List<FieldValue> loadFieldValueFieldCollectionBatch(List<UUID> ids);

    @EntityGraph(attributePaths = {"geometryValues"})
    @Query("select fv from FieldValue fv where fv.id in :ids")
    List<FieldValue> loadFieldValueGeometryBatch(List<UUID> ids);

    @EntityGraph(attributePaths = {"externalValues"})
    @Query("select fv from FieldValue fv where fv.id in :ids")
    List<FieldValue> loadFieldValueExternalBatch(List<UUID> ids);
}
