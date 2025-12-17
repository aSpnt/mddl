package ru.softmachine.odyssey.backend.cms.exception;

import lombok.Getter;

/**
 * Исключение выбрасываеся в случаях, когда при сохранении обнаруживается
 * несоотвествие счетчика версии.
 */
@Getter
public class OptimisticLockProtectionException extends RuntimeException {

    private Long version;
    private String id;

    public OptimisticLockProtectionException(String message, String id, Long version) {
        super(message + " id: " + id + ", version: " + version);
        this.id = id;
        this.version = version;
    }
}
