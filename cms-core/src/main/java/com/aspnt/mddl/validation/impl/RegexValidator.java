package com.aspnt.mddl.validation.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import com.aspnt.mddl.dto.FieldValueDto;
import com.aspnt.mddl.dto.validation.ConstraintViolation;
import com.aspnt.mddl.entity.validation.FieldValidation;
import com.aspnt.mddl.dto.validation.ValidationType;
import com.aspnt.mddl.validation.Validator;

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
