package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity;

@jakarta.persistence.Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "entity_comment", schema = "meta")
@Accessors(chain = true)
public class EntityComment extends BaseEntity {

    private String title;

    private String message;

    @ManyToOne
    @JoinColumn(name = "entity_id")
    private Entity entity;

    @Column(name = "author")
    private String author;

    @Column(name = "author_email")
    private String authorEmail;
}
