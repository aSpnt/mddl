package com.aspnt.mddl.validation.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.aspnt.mddl.dto.FieldValueDto;
import com.aspnt.mddl.entity.validation.FieldValidation;
import com.aspnt.mddl.dto.validation.ValidationType;
import com.aspnt.mddl.dto.validation.ConstraintViolation;
import com.aspnt.mddl.validation.Validator;

import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class MaxLengthValidator implements Validator {

    @Override
    public ConstraintViolation validate(
            FieldValidation fieldValidation,
            FieldValueDto fieldValue,
            Map<String, Object> entityContext,
            String path) {

        var maxLength = fieldValidation.getIntValue();
        if (maxLength == null || fieldValue.getTextValue() == null || fieldValue.getFieldDef().getType() == null) {
            log.warn("Max length or fieldValue or fieldDef is null, aborting validation. Path: {}", path);
            return null;
        }

        var isValid = switch (fieldValue.getFieldDef().getType()) {
            case TEXT, STRING, JSON, HTML -> fieldValue.getTextValue().length() <= maxLength;
            default -> true;
        };

        if (!isValid) {
            return new ConstraintViolation(
                    fieldValidation.getMessage(),
                    path,
                    fieldValidation.getType());
        }
        return null;
    }

    @Override
    public Set<ValidationType> getValidationTypes() {
        return Set.of(ValidationType.MAX_LENGTH);
    }
}
