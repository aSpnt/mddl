package ru.softmachine.odyssey.backend.cms.validation.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueDto;
import ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation;
import ru.softmachine.odyssey.backend.cms.dto.validation.ValidationType;
import ru.softmachine.odyssey.backend.cms.dto.validation.ConstraintViolation;
import ru.softmachine.odyssey.backend.cms.validation.Validator;

import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class MaxQuantityValidator implements Validator {

    @Override
    public ConstraintViolation validate(
            FieldValidation fieldValidation,
            FieldValueDto fieldValue,
            Map<String, Object> entityContext,
            String path) {

        var maxQuantity = fieldValidation.getIntValue();
        if (maxQuantity == null || fieldValue.getTextValue() == null || fieldValue.getFieldDef().getType() == null) {
            log.warn("Max quantity or fieldValue or fieldDef is null, aborting validation. Path: {}", path);
            return null;
        }

        var isValid = switch (fieldValue.getFieldDef().getType()) {
            case COLLECTION -> fieldValue.getEntities().size() <= maxQuantity;
            case DICTIONARY ->  (fieldValue.getRefValueCollection() == null
                                    || fieldValue.getRefValueCollection().size() <= maxQuantity);
            case DICTIONARY_EXTERNAL -> fieldValue.getExternalValues().size()  <= maxQuantity;
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
        return Set.of(ValidationType.MAX_QUANTITY);
    }
}
