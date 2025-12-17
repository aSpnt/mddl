package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;

/**
 * Режим отображения дефиниции
 */
@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "entity_def_mode", schema = "meta")
@Accessors(chain = true)
public class EntityDefMode extends BaseEntity {

    private String name;

    private String code;

    @ManyToOne
    @JoinColumn(name = "entity_def_id")
    private EntityDef entityDef;
}
