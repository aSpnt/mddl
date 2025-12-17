package ru.softmachine.odyssey.backend.cms.entity.validation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.dto.validation.ValidationType;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity;

import java.util.List;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "field_validation", schema = "meta")
@Accessors(chain = true)
public class FieldValidation extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ValidationType type;

    @Column(name = "text_value")
    private String textValue;

    @Column(name = "double_value")
    private Double doubleValue;

    @Column(name = "int_value")
    private Integer intValue;

    @Column(name = "text_array_value")
    private List<String> textArrayValue;

    @ManyToOne
    @JoinColumn(name = "field_def_id")
    private FieldDef fieldDef;

    @Column(name = "message")
    private String message;
}
