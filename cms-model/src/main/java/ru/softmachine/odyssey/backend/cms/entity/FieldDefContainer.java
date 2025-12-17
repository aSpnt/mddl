package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity;
import ru.softmachine.odyssey.backend.cms.dto.base.FieldDefContainerType;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;

import java.util.ArrayList;
import java.util.List;

/**
 * Контейнер полей, позволяет группировать поля в иерархические группы
 * и настраивать отображение в генерируемых формах.
 *
 * На текущий момент наличие контейнера не влияет на уровни при построении DTO,
 * но, вероятно, это будет настаиваемым поведением.
 */
@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "field_def_container", schema = "meta")
@Accessors(chain = true)
public class FieldDefContainer extends BaseEntity {

    private String name;

    private String code;

    private Integer seq;

    /**
     * Условие (js) для видимости контейнера на
     * форме заведения данных
     */
    @Column(name = "visible_condition")
    private String visibleCondition;

    /**
     * Условие (js) для видимости контейнера на
     * форме заведения данных
     */
    @Column(name = "disable_condition")
    private String disableCondition;

    @Enumerated(EnumType.STRING)
    private FieldDefContainerType type;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private FieldDefContainer parent;

    @Fetch(FetchMode.JOIN)
    @OrderBy("seq")
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<FieldDefContainer> childContainers = new ArrayList<>();

    @Fetch(FetchMode.JOIN)
    @OrderBy("seq")
    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FieldDef> fields = new ArrayList<>();

    public void setFields(List<FieldDef> fields) {
        if (CollectionUtils.isNotEmpty(this.fields)) {
            // unlink from container old fields (orphanRemoval disabled)
            this.fields.forEach(fieldDef -> fieldDef.setContainer(null));
        }
        this.fields.clear();
        if (fields != null) {
            this.fields.addAll(fields);
        }
    }

    public void setChildContainers(List<FieldDefContainer> childContainers) {
        this.childContainers.clear();
        if (childContainers != null) {
            this.childContainers.addAll(childContainers);
        }
    }
}
