package ru.softmachine.odyssey.backend.cms.validation.impl;

import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueDto;
import ru.softmachine.odyssey.backend.cms.dto.validation.ConstraintViolation;
import ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation;
import ru.softmachine.odyssey.backend.cms.dto.validation.ValidationType;
import ru.softmachine.odyssey.backend.cms.validation.Validator;

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
