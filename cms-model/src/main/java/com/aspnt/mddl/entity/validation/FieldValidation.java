package com.aspnt.mddl.entity.validation;

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
import com.aspnt.mddl.dto.validation.ValidationType;
import com.aspnt.mddl.entity.field.FieldDef;
import com.aspnt.mddl.entity.base.BaseEntity;

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
