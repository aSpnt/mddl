package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.softmachine.odyssey.backend.cms.dto.EntityTemplateStatus;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;

import java.util.ArrayList;
import java.util.List;

@jakarta.persistence.Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "entity_template", schema = "meta")
@Accessors(chain = true)
public class EntityTemplate extends BaseEntity {

    private String name;

    private String code;

    private Integer seq;

    private String description;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EntityTemplateStatus status;

    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @JoinColumn(name = "entity_def_id")
    private EntityDef entityDef;

    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "entityTemplate", cascade = CascadeType.ALL)
    private List<FieldValue> values = new ArrayList<>();
}
