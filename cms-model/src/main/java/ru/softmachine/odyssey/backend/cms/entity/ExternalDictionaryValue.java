package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

@jakarta.persistence.Entity
@Data
@ToString(callSuper = true)
@Table(name = "dictionary_external_value", schema = "meta")
@Accessors(chain = true)
@IdClass(ExternalDictionaryId.class)
public class ExternalDictionaryValue {

    @Id
    private String id;

    @Id
    private Integer seq;

    @Column(name = "created_ts", updatable = false)
    private ZonedDateTime createdTs;

    @Column(name = "updated_ts")
    private ZonedDateTime updatedTs;

    private String name;

    private String description;

    private String img;

    private String ref;

    @Id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_value_id")
    private FieldValue fieldValue;

    @PrePersist
    protected void onCreate() {
        createdTs = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTs = ZonedDateTime.now();
    }
}
