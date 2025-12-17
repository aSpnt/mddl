package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@jakarta.persistence.Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "field_value", schema = "meta")
@Accessors(chain = true)
public class FieldValue extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_def_id")
    private FieldDef fieldDef;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id")
    private Entity entity;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_template_id")
    private EntityTemplate entityTemplate;

    /**
     * Переопределение признака видимости (для скрытия поля в шаблонах)
     */
    @Column(name = "visible_override")
    private Boolean visibleOverride;

    /**
     * Переопределение признака редактирования (для фиксации значения)
     */
    @Column(name = "disabled_override")
    private Boolean disabledOverride;

    /**
     * Переопределение очередности от definition
     */
    @Column(name = "seq_override")
    private Integer seqOverride;

    /**
     * Переопределение обязательности от definition
     */
    @Column(name = "required_override")
    private Boolean requiredOverride;

    @Column(name = "allow_templates_override")
    private List<String> allowTemplatesOverride;

    @Column(name = "predefined_selections_override")
    private List<String> predefinedSelectionsOverride;

    @Column(name = "boolean_value")
    private Boolean booleanValue;

    @Column(name = "double_value")
    private Double doubleValue;

    @Column(name = "int_value")
    private Long intValue;

    @Column(name = "text_value")
    private String textValue;

    @Column(name = "date_value")
    private LocalDate dateValue;

    @Column(name = "time_value")
    private LocalTime timeValue;

    @Column(name = "datetime_value")
    private ZonedDateTime datetimeValue;

    @Column(name = "array_text")
    private List<String> arrayText;

    @Column(name = "array_date")
    private List<LocalDate> arrayDate;

    @Column(name = "array_datetime")
    private List<ZonedDateTime> arrayDateTime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "ref_value")
    private Entity refValue;

    @ManyToOne(cascade = {CascadeType.ALL})
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "entity_value")
    private Entity entityValue;

    /**
     * Множественные ссылки на существующие entity (каскадно не сохраняются)
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            schema = "meta",
            name = "ref_value_collection",
            joinColumns = {@JoinColumn(name = "field_value_id")},
            inverseJoinColumns = {@JoinColumn(name = "entity_id")}
    )
    private List<Entity> refValueCollection = new ArrayList<>();

    /**
     * Вложенные сущности (каскадно сохраняются)
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Fetch(FetchMode.JOIN)
    @OrderBy("seq")
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    // опасная аннотация, но концептуально field_collection это one-to-many и удаление корректно
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinTable(
            schema = "meta",
            name = "field_collection",
            joinColumns = {@JoinColumn(name = "field_value_id")},
            inverseJoinColumns = {@JoinColumn(name = "entity_id")}
    )
    private List<Entity> entities = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OrderBy("seq")
    @OneToMany(mappedBy = "fieldValue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExternalDictionaryValue> externalValues = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "fieldValue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FieldGeometryValue> geometryValues = new ArrayList<>();

    public void setExternalValues(List<ExternalDictionaryValue> newExternalValues) {
        if (newExternalValues != null) {
            var result = newExternalValues.stream()
                    .map(newValue -> this.externalValues.stream().filter(oldValue ->
                            oldValue.getId().equals(newValue.getId())
                                    && oldValue.getSeq().equals(newValue.getSeq())
                    ).findFirst().orElse(newValue))
                    .toList();
            this.externalValues.clear();
            this.externalValues.addAll(result);
        }
    }

    public void setEntities(List<Entity> newEntities) {
        this.entities.clear();
        if (newEntities != null) {
            this.entities.addAll(newEntities);
        }
    }

    public void setRefValueCollection(List<Entity> newRefValueCollection) {
        this.refValueCollection.clear();
        if (newRefValueCollection != null) {
            this.refValueCollection.addAll(newRefValueCollection);
        }
    }

    public void setGeometryValues(List<FieldGeometryValue> geometryValues) {
        this.geometryValues.clear();
        if (geometryValues != null) {
            this.geometryValues.addAll(geometryValues);
        }
    }
}
