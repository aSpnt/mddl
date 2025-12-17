package ru.softmachine.odyssey.backend.cms.evaluation;

import ru.softmachine.odyssey.backend.cms.dto.base.FieldType;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;

import java.util.Map;

/**
 * Универсальный интерфейс для обработчиков
 *
 * @param <T> тип возвращаемого значения
 */
public interface EvaluationProcessor<T> {

    /**
     * Возвращает тип поля (возможно стоит расширить до коллекции), который рассчитывает данный процессор
     * @return
     */
    FieldType getFieldType();

    /**
     * Вычисление значения
     *
     * @param expression шаблон или выражение
     * @param entityContext контекст значений для вычисления
     * @return рассчитанное значение
     */
    T evaluate(String expression, Map<String, Object> entityContext, FieldDef fieldDef);
}
