package com.aspnt.mddl.validation.impl;

import org.springframework.stereotype.Component;
import com.aspnt.mddl.dto.FieldValueDto;
import com.aspnt.mddl.dto.validation.ConstraintViolation;
import com.aspnt.mddl.entity.validation.FieldValidation;
import com.aspnt.mddl.dto.validation.ValidationType;
import com.aspnt.mddl.validation.Validator;

import java.util.Map;
import java.util.Set;


@Component
public class MinValidator implements Validator {

    @Override
    public ConstraintViolation validate(
            FieldValidation fieldValidation,
            FieldValueDto fieldValue,
            Map<String, Object> entityContext,
            String path
    ) {
        var isValid = switch (fieldValidation.getType()) {
            case MIN -> {
                if ((fieldValue.getIntValue() == null) && (fieldValue.getDoubleValue() == null)) {
                    // если значения не заданы, то валидация не выполняется
                    yield true;
                }
                yield ((fieldValue.getIntValue() != null
                        && fieldValue.getIntValue() >= fieldValidation.getIntValue())
                        || (fieldValue.getDoubleValue() != null
                        && fieldValue.getDoubleValue() >= fieldValidation.getDoubleValue()));
            }
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
        return Set.of(ValidationType.MIN, ValidationType.MIN_LENGTH, ValidationType.MIN_QUANTITY);
    }
}
