package com.aspnt.mddl.validation.impl;

import org.springframework.stereotype.Component;
import com.aspnt.mddl.dto.FieldValueDto;
import com.aspnt.mddl.dto.validation.ConstraintViolation;
import com.aspnt.mddl.entity.validation.FieldValidation;
import com.aspnt.mddl.dto.validation.ValidationType;
import com.aspnt.mddl.validation.Validator;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


@Component
public class EmailValidator implements Validator {

    @Override
    public ConstraintViolation validate(
            FieldValidation fieldValidation,
            FieldValueDto fieldValue,
            Map<String, Object> entityContext,
            String path
    ) {
        if (fieldValue.getTextValue() == null ||
                !Pattern.matches("^(.+)@(\\S+)$", fieldValue.getTextValue())) {
            return new ConstraintViolation(
                    fieldValidation.getMessage(),
                    fieldValidation.getId().toString(),
                    fieldValidation.getType());
        }
        return null;
    }

    @Override
    public Set<ValidationType> getValidationTypes() {
        return Set.of(ValidationType.EMAIL);
    }
}
