package ru.softmachine.odyssey.backend.cms.evaluation.spel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import ru.softmachine.odyssey.backend.cms.dto.base.FieldType;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;
import ru.softmachine.odyssey.backend.cms.evaluation.EvaluationProcessor;

import java.util.Collection;
import java.util.Map;

/**
 * Сервис предназначен для формирования расчетной
 * коллекции на основе базовой
 */
@Slf4j
@Service
public class FilteredCollectionProcessor implements EvaluationProcessor<Object> {

    private static final String ITEM_CODE = "__item";

    @Override
    public FieldType getFieldType() {
        return FieldType.COLLECTION_FILTERED;
    }

    @Override
    public Object evaluate(String expression, Map<String, Object> entityContext, FieldDef fieldDef) {
        if (expression == null) {
            log.warn("Empty expression for field def: {}", fieldDef.getId());
            return null;
        }
        if (StringUtils.isBlank(fieldDef.getRefCollectionFieldCode())) {
            log.warn("Не найдена ссылка на коллекцию для поля с кодом: {}", fieldDef.getCode());
            return null;
        }

        var contextData = new EvaluationDataContextHolder(entityContext);
        var context = new StandardEvaluationContext(contextData);
        try {
            var parser = new SpelExpressionParser();
            var exp = parser.parseExpression(expression);

            var baseCollection = entityContext.get(fieldDef.getRefCollectionFieldCode());
            if (baseCollection == null) {
                log.warn("Коллекция с кодом '{}' не найдена в контексте", fieldDef.getRefCollectionFieldCode());
                return null;
            }
            if (!(baseCollection instanceof Collection)) {
                log.warn("Значение с кодом '{}' не является коллекцией", fieldDef.getRefCollectionFieldCode());
                return null;
            }

            return ((Collection) baseCollection).stream().filter(item -> {
                context.setVariable(ITEM_CODE, item);
                return exp.getValue(context, Boolean.class);
            }).toList();

        } catch (SpelParseException e) {
            log.error("Ошибка парсинга фильтрующего выражения '{}': {}", expression, e.getMessage());
        } catch (Exception e) {
            log.warn("Ошибка исполнения фильтрующего выражения '{}': {}", expression, e.getMessage());
        }

        return null;
    }
}
