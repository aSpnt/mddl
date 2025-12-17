package ru.softmachine.odyssey.backend.cms.exception;

import lombok.Getter;
import ru.softmachine.odyssey.backend.cms.dto.validation.ConstraintViolation;

import java.util.List;

/**
 * Исключение выбрасывается в случае найденных ошибок при сохранении или обновлении сущности
 */
@Getter
public class ValidationException extends RuntimeException {

    private final List<ConstraintViolation> violations;

    public ValidationException(String message, List<ConstraintViolation> violations) {
        super(message);
        this.violations = violations;
    }
}
