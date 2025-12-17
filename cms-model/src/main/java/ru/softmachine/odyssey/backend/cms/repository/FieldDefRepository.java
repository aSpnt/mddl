package ru.softmachine.odyssey.backend.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDefIdWithTypeProj;
import ru.softmachine.odyssey.backend.cms.dto.field.FieldDefType;

import java.util.Optional;
import java.util.UUID;

public interface FieldDefRepository extends JpaRepository<FieldDef, UUID>,
        JpaSpecificationExecutor<FieldDef> {

    @Query("select fd from FieldDef fd where fd.id = :id and fd.fieldDefType = :type")
    Optional<FieldDef> findByIdAndType(UUID id, FieldDefType type);

    @Query(value = """
             select edrfd.field_def_id as id, edrfd.field_def_type as type from meta.entity_def_ref_field_def edrfd
             where edrfd.field_def_code = :fieldDefCode and edrfd.entity_def_id = :entityDefId
             limit 1
            """, nativeQuery = true)
    Optional<FieldDefIdWithTypeProj> getFieldDefIdAndTypeByCode(UUID entityDefId, String fieldDefCode);

    @Query(value = """
             select edrfd.field_def_id from meta.entity_def_ref_field_def edrfd
             where edrfd.field_def_code = :fieldDefCode and edrfd.entity_def_id = :entityDefId
             limit 1
            """, nativeQuery = true)
    Optional<UUID> getFieldDefIdByCode(UUID entityDefId, String fieldDefCode);
}
