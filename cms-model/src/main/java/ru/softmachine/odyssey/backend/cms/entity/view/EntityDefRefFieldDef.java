package ru.softmachine.odyssey.backend.cms.entity.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Entity
@Data
@ToString(callSuper = true)
@IdClass(EntityDefEntityFieldId.class)
@Table(name = "entity_def_ref_field_def", schema = "meta")
@Immutable
public class EntityDefRefFieldDef {

    @Id
    private UUID entityDefId;

    @Id
    private UUID fieldDefId;

    private String entityDefCode;

    private String fieldDefCode;
}
