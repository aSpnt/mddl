package ru.softmachine.odyssey.backend.cms.entity;

import io.hypersistence.utils.hibernate.type.search.PostgreSQLTSVectorType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@jakarta.persistence.Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "entity", schema = "meta")
@Accessors(chain = true)
public class Entity extends BaseEntity {

    private String slug;

    @Version
    private Long version;

    private Integer seq;

    @Column(name = "last_status_change_ts")
    private ZonedDateTime lastStatusChangeTs;

    @Column(name = "slug_lock")
    private boolean slugLock;

    @Column(name = "delete_lock")
    private boolean deleteLock;

    @Column(name = "active")
    private boolean active;

    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @JoinColumn(name = "entity_def_id")
    private EntityDef entityDef;

    // TODO: orphan removal
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL)
    private List<FieldValue> values = new ArrayList<>();

    @Type(PostgreSQLTSVectorType.class)
    @Column(name = "fts_vector")
    private String ftsVector;

    @Column(name = "author")
    private String author;

    @Column(name = "author_email")
    private String authorEmail;

    /**
     * Пока сохраняем только название, так как ссылка на сузность не предполашает использования,
     * сущность создана на основе шаблона и далее они не связаны. Но название принято решение сохранить
     */
    @Column(name = "entity_template_name")
    private String entityTemplateName;
}
