package ru.softmachine.odyssey.backend.cms.exception;

import lombok.Getter;

/**
 * Исключение выбрасывается в случаях, когда указанный референс
 * не может быть удалён из-за foreign_key constraint
 */
@Getter
public class EntityReferenceException  extends RuntimeException {

    private String id;
    private Throwable cause;

    public EntityReferenceException(String message, String id, Throwable cause) {
        super(message);
        this.id = id;
        this.cause = cause;
    }
}
