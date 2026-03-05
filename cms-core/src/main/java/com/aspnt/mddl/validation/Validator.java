package com.aspnt.mddl.validation;

import com.aspnt.mddl.dto.FieldValueDto;
import com.aspnt.mddl.dto.validation.ConstraintViolation;
import com.aspnt.mddl.entity.validation.FieldValidation;
import com.aspnt.mddl.dto.validation.ValidationType;

import java.util.Map;
import java.util.Set;

/**
 * Контракт валидатора сущности
 */
public interface Validator {

    /**
     * Вызов валидации
     *
     * @param fieldValidation дефиниция поля, значение которого валидируется
     * @param fieldValue значение поля
     * @param entityContext контекст в котором происходит валидация
     * @return null, если ошибок валидации не обнаружено, ошибка валидации, если выявлено несоответствие
     */
    ConstraintViolation validate(
            FieldValidation fieldValidation,
            FieldValueDto fieldValue,
            Map<String, Object> entityContext,
            String path
    );

    /**
     * Возвращает коллекцию типов валидации, которую обработывает
     */
    Set<ValidationType> getValidationTypes();
}
