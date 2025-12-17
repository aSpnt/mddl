package ru.softmachine.odyssey.backend.cms.evaluation.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.softmachine.odyssey.backend.cms.dto.base.FieldType;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;
import ru.softmachine.odyssey.backend.cms.evaluation.EvaluationProcessor;
import ru.softmachine.odyssey.backend.cms.service.ValueExternalService;

import java.util.Map;

/**
 * Сервис предназначен для получения значений из внешних сервисов
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalValueProcessor implements EvaluationProcessor<Object> {

    private final ValueExternalService externalService;

    @Override
    public FieldType getFieldType() {
        return FieldType.EXTERNAL_VALUE;
    }

    @Override
    public Object evaluate(String expression, Map<String, Object> entityContext, FieldDef fieldDef) {
        // TODO: тут конечно нужно передавать connection, но в целях оптимизации пока этого не делаем
        // чтобы не решать проблемы с пустыми значениями в кеше и не генерировать для них множество запросов
        return externalService.getValueById(entityContext.get("id").toString(), fieldDef.getId());
    }
}
