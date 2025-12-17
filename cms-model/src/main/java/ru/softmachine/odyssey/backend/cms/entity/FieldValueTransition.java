package ru.softmachine.odyssey.backend.cms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;

import java.util.List;

@jakarta.persistence.Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "field_value_transition", schema = "meta")
@Accessors(chain = true)
public class FieldValueTransition extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_def_id")
    private FieldDef fieldDef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_from_id")
    private Entity entityFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_to_id")
    private Entity entityTo;

    @Column(name = "show_button")
    private boolean showButton;

    /**
     * Указывает на необходимость обновить время изменения статуса
     */
    @Column(name = "update_last_status_ts")
    private boolean updateLastStatusTs;

    /**
     * Устанавливает флаг активности для сущности после перехода
     */
    @Column(name = "entity_active_status")
    private boolean entityActiveStatus;

    @Column(name = "comment_title")
    private String commentTitle;

    private String message;

    private int seq;

    /**
     * Флаг указывающий на возможность заполнения комментария при переходе статусов
     */
    @Column(name = "need_comment")
    private boolean needComment;

    /**
     * Флаг указывающий на обязательность комментария при переходе статусов
     */
    @Column(name = "comment_required")
    private boolean commentRequired;

    /**
     * Комментарий к полю комментария
     */
    @Column(name = "comment_note")
    private String commentNote;

    /**
     * Поля которые нужно заполнить при переходе статусов
     */
    @Column(name = "field_codes")
    private List<String> fieldCodes;
}
