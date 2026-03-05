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
public class PhoneValidator implements Validator {

    private static final Pattern PHONE_PATTERN = Pattern.compile("(^[+]?\\d{11}$)");

    @Override
    public ConstraintViolation validate(
            FieldValidation fieldValidation,
            FieldValueDto fieldValue,
            Map<String, Object> entityContext,
            String path
    ) {
        if (fieldValue.getTextValue() != null &&
                !PHONE_PATTERN.matcher(fieldValue.getTextValue()).matches()) {
            return new ConstraintViolation(
                    fieldValidation.getMessage(),
                    path,
                    fieldValidation.getType());
        }

        return null;
    }

    @Override
    public Set<ValidationType> getValidationTypes() {
        return Set.of(ValidationType.PHONE);
    }
}


