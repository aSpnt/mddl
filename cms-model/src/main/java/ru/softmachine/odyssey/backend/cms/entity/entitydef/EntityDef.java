package ru.softmachine.odyssey.backend.cms.entity.entitydef;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.softmachine.odyssey.backend.cms.dto.DictionaryHttpMethod;
import ru.softmachine.odyssey.backend.cms.entity.EntityDefExternalHeader;
import ru.softmachine.odyssey.backend.cms.entity.EntityDefGroup;
import ru.softmachine.odyssey.backend.cms.entity.EntityDefMode;
import ru.softmachine.odyssey.backend.cms.dto.EntityDefStatus;
import ru.softmachine.odyssey.backend.cms.entity.EntityTemplate;
import ru.softmachine.odyssey.backend.cms.entity.FieldDefContainer;
import ru.softmachine.odyssey.backend.cms.dto.GlobalSearchType;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity;
import ru.softmachine.odyssey.backend.cms.dto.provider.ProviderType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "entity_def", schema = "meta")
@Accessors(chain = true)
public class EntityDef extends BaseEntity {

    @Version
    private Long version;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    private String name;

    private String nominative;

    private String genitive;

    @Column(name = "template_note")
    private String templateNote;

    @Column(name = "success_create_message")
    private String successCreateMessage;

    @Column(name = "success_delete_message")
    private String successDeleteMessage;

    @Column(name = "on_delete_conflict_message")
    private String onDeleteConflictMessage;

    private String code;

    private boolean root;

    private boolean singleton;

    @Column(name = "allow_inline_creation")
    private boolean allowInlineCreation;

    @Column(name = "lock_creating")
    private boolean lockCreating;

    @Column(name = "allow_dnd")
    private boolean allowDnd;

    @Column(name = "show_comments")
    private boolean showComments;

    // for external rest entities
    private String url;

    @Column(name = "url_list")
    private String urlList;

    @Enumerated(EnumType.STRING)
    private DictionaryHttpMethod method;

    @Column(name = "response_param")
    private String responseParam;

    @Column(name = "default_body")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> defaultBody;

    // настройки пагинации для внешних сущностей
    @Column(name = "page_filter_name")
    private String pageFilterName;

    @Column(name = "page_filter_size_name")
    private String pageFilterSizeName;

    @Column(name = "page_filter_number_name")
    private String pageFilterNumberName;

    @Column(name = "response_total_name")
    private String responseTotalName;

    @Enumerated(EnumType.STRING)
    @Column(name = "global_search_type")
    private GlobalSearchType globalSearchType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_def_group_id")
    private EntityDefGroup entityDefGroup;

    // каскада нет, так как управление шаблонами идет через отдельное АПИ
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "entityDef", fetch = FetchType.LAZY)
    private List<EntityTemplate> templates = new ArrayList<>();

    // каскада нет, так как управление режимами идет через отдельное АПИ
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "entityDef", fetch = FetchType.LAZY)
    private List<EntityDefMode> modes = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "singleton_entity_id")
    private ru.softmachine.odyssey.backend.cms.entity.Entity singletonEntity;

    @Enumerated(EnumType.STRING)
    private EntityDefStatus status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "container_id")
    private FieldDefContainer container;

    @OneToMany(mappedBy = "entityDef", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntityDefExternalHeader> headers = new ArrayList<>();

    public void setHeaders(List<EntityDefExternalHeader> headers) {
        this.headers.clear();
        if (headers != null) {
            this.headers.addAll(headers);
        }
    }

    public void setTemplates(List<EntityTemplate> templates) {
        this.templates.clear();
        if (templates != null) {
            this.templates.addAll(templates);
        }
    }
}
