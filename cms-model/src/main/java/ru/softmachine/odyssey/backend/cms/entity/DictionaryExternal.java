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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.softmachine.odyssey.backend.cms.dto.DictionaryHttpMethod;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@jakarta.persistence.Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "dictionary_external", schema = "meta")
@Accessors(chain = true)
public class DictionaryExternal extends BaseEntity {

    private String name;

    private String code;

    @Column(name = "is_spel")
    private Boolean isSpel;

    @Column(name = "id_param")
    private String idParam;

    @Column(name = "name_param")
    private String nameParam;

    @Column(name = "description_param")
    private String descriptionParam;

    @Column(name = "description_expression")
    private String descriptionExpression;

    @Column(name = "img_param")
    private String imgParam;

    @Column(name = "img_expression")
    private String imgExpression;

    @Column(name = "ref_expression")
    private String refExpression;

    private String url;

    @Enumerated(EnumType.STRING)
    private DictionaryHttpMethod method;

    /**
     * Путь до свойства с поисковым полем
     */
    private List<String> param;

    @Column(name = "response_param")
    private String responseParam;

    @Column(name = "default_body")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> defaultBody; // строковое значение является упрощением

    @OneToMany(mappedBy = "dictionaryExternal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DictionaryExternalHeader> headers = new ArrayList<>();

    @Fetch(FetchMode.JOIN)
    @ManyToOne
    @JoinColumn(name = "entity_def_id")
    private EntityDef entityDef;

    public void setHeaders(List<DictionaryExternalHeader> headers) {
        this.headers.clear();
        if (headers != null) {
            this.headers.addAll(headers);
        }
    }
}
