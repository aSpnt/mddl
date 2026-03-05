package com.aspnt.mddl.validation.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.aspnt.mddl.dto.FieldValueDto;
import com.aspnt.mddl.dto.validation.ConstraintViolation;
import com.aspnt.mddl.entity.validation.FieldValidation;
import com.aspnt.mddl.dto.validation.ValidationType;
import com.aspnt.mddl.evaluation.spel.SimpleEvaluationProcessor;
import com.aspnt.mddl.validation.Validator;

import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExpressionValidator implements Validator {

    private final SimpleEvaluationProcessor evaluationService;

    @Override
    public ConstraintViolation validate(
            FieldValidation fieldValidation,
            FieldValueDto fieldValue,
            Map<String, Object> entityContext,
            String path
    ) {
        var expression = fieldValidation.getTextValue();
        var result = evaluationService.evaluate(expression, entityContext);

        if (result instanceof Boolean) {
            if (Boolean.FALSE.equals(result)) {
                return new ConstraintViolation(
                        fieldValidation.getMessage(),
                        path,
                        ValidationType.EXPRESSION);
            }
        } else {
            log.warn("Expression evaluator calculates non-boolean value: {}", result);
        }

        return null;
    }

    @Override
    public Set<ValidationType> getValidationTypes() {
        return Set.of(ValidationType.EXPRESSION);
    }
}
