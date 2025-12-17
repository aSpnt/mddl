package ru.softmachine.odyssey.backend.cms.exception;

import lombok.Getter;
import lombok.ToString;

/**
 * Исключение выбрасываеся в случаях, когда указанный референс
 * не может быть разрешен.
 */
@ToString
@Getter
public class EntityNotFoundException extends RuntimeException {

    private String id;

    public EntityNotFoundException(String message, String id) {
        super(message);
        this.id = id;
    }
}
