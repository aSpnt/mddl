package ru.softmachine.odyssey.backend.cms.validation.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueDto;
import ru.softmachine.odyssey.backend.cms.dto.validation.ConstraintViolation;
import ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation;
import ru.softmachine.odyssey.backend.cms.dto.validation.ValidationType;
import ru.softmachine.odyssey.backend.cms.evaluation.spel.SimpleEvaluationProcessor;
import ru.softmachine.odyssey.backend.cms.validation.Validator;

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
