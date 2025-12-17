package ru.softmachine.odyssey.backend.cms.validation.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueDto;
import ru.softmachine.odyssey.backend.cms.dto.validation.ConstraintViolation;
import ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation;
import ru.softmachine.odyssey.backend.cms.dto.validation.ValidationType;
import ru.softmachine.odyssey.backend.cms.validation.Validator;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Slf4j
@Component
public class RegexValidator implements Validator {

    @Override
    public ConstraintViolation validate(
            FieldValidation fieldValidation,
            FieldValueDto fieldValue,
            Map<String, Object> entityContext,
            String path
    ) {
        var regex = fieldValidation.getTextValue();
        var input = fieldValue.getTextValue();

        try {
            if (StringUtils.isNotBlank(input) && !Pattern.matches(regex, input)) {
                return new ConstraintViolation(
                        fieldValidation.getMessage(),
                        path,
                        fieldValidation.getType());
            }
        } catch (PatternSyntaxException e) {
            log.warn(e.getMessage());
        }

        return null;
    }

    @Override
    public Set<ValidationType> getValidationTypes() {
        return Set.of(ValidationType.REGEX);
    }
}
