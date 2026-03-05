package com.aspnt.mddl.entity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.aspnt.mddl.entity.base.BaseEntity;
import com.aspnt.mddl.entity.entitydef.EntityDef;

@jakarta.persistence.Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "entity_def_external_header", schema = "meta")
@Accessors(chain = true)
public class EntityDefExternalHeader extends BaseEntity {

    private String name;

    private String value;

    @Column(name = "is_spel")
    private Boolean isSpel;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "entity_def_external_id")
    private EntityDef entityDef;
}
