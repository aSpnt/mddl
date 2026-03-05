package com.aspnt.mddl.evaluation.template;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.aspnt.mddl.dto.base.FieldType;
import com.aspnt.mddl.entity.field.FieldDef;
import com.aspnt.mddl.evaluation.EvaluationProcessor;
import java.util.Map;

/**
 * Сервис для рендеринга шаблонов на основе Mustache
 */
@Slf4j
@Service
public class TemplateService implements EvaluationProcessor<String> {

    @Override
    public FieldType getFieldType() {
        return FieldType.HTML_TEMPLATE;
    }

    /**
     * Выполняет расчет шаблона на основе предварительно собранного контекста
     *
     * @param template шаблон
     * @param contextData карта значений сущности
     * @return заполненный шаблон или null, в случае ошибок
     */
    public String evaluate(String template, Map<String, Object> contextData, FieldDef fieldDef) {
        if (template == null) {
            log.warn("Empty template for field def: {}", fieldDef.getId());
            return null;
        }
        Template tmpl = Mustache.compiler()
                //.escapeHTML(false) // на самом деле вряд ли пригодится, случай HTML значения
                .defaultValue("-")
                .compile(template);
        try {
            return tmpl.execute(contextData);
        } catch (Exception e) {
            log.error("Ошибка вычисления шаблона", e);
        }
        return null;
    }
}
