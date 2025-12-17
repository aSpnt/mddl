package ru.softmachine.odyssey.backend.cms.validation.impl;

import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueDto;
import ru.softmachine.odyssey.backend.cms.dto.validation.ConstraintViolation;
import ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation;
import ru.softmachine.odyssey.backend.cms.dto.validation.ValidationType;
import ru.softmachine.odyssey.backend.cms.validation.Validator;

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
