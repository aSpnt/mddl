package ru.softmachine.odyssey.backend.cms.evaluation.spel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import ru.softmachine.odyssey.backend.cms.dto.base.FieldType;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;
import ru.softmachine.odyssey.backend.cms.evaluation.EvaluationProcessor;

import java.util.Map;

/**
 * Сервис предназначен для формирования контекста выполнения
 * и исполнения расчета динамических выражений
 */
@Slf4j
@Service
public class SimpleEvaluationProcessor implements EvaluationProcessor<Object> {

    @Override
    public FieldType getFieldType() {
        return FieldType.EXPRESSION;
    }

    @Override
    public Object evaluate(String expression, Map<String, Object> entityContext, FieldDef fieldDef) {
        return evaluate(expression, entityContext);
    }

    public Object evaluate(String expression, Map<String, Object> entityContext) {
        if (expression == null) {
            return null;
        }
        var contextData = new EvaluationDataContextHolder(entityContext);
        var context = new StandardEvaluationContext(contextData);
        try {
            var parser = new SpelExpressionParser();
            var exp = parser.parseExpression(expression);

            return exp.getValue(context, Object.class);
        } catch (SpelParseException e) {
            log.warn("Ошибка парсинга расчетного выражения '{}': {}", expression, e.getMessage());
        } catch (Exception e) {
            log.warn("Ошибка исполнения расчетного выражения '{}': {}", expression, e.getMessage());
        }

        return null;
    }
}
