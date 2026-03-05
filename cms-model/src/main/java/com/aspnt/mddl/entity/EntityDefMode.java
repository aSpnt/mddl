package com.aspnt.mddl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.aspnt.mddl.entity.base.BaseEntity;
import com.aspnt.mddl.entity.entitydef.EntityDef;

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
