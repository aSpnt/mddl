package ru.softmachine.odyssey.backend.cms.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.ZonedDateTime;

/**
 * Базовый класс для типовых сущностей
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class BaseEntity extends UidIdentEntity {

    @Column(name = "created_ts", updatable = false)
    private ZonedDateTime createdTs;

    @Column(name = "updated_ts")
    private ZonedDateTime updatedTs;

    @PrePersist
    protected void onCreate() {
        createdTs = ZonedDateTime.now();
        updatedTs = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTs = ZonedDateTime.now();
    }
}
