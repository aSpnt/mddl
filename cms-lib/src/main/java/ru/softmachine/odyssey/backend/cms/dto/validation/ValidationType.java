package ru.softmachine.odyssey.backend.cms.dto.validation;

public enum ValidationType {
    REGEX,
    EMAIL,
    PHONE,
    MAX,
    MIN,
    MIN_QUANTITY,
    MAX_QUANTITY,
    MIN_LENGTH,
    MAX_LENGTH,
    EXPRESSION,
    MEDIA_FORMAT,
    MEDIA_MAX_SIZE,
    MEDIA_MIN_HEIGHT,
    MEDIA_MAX_HEIGHT,
    MEDIA_MIN_WIDTH,
    MEDIA_MAX_WIDTH,
    TIMESTAMP_AFTER_DURATION,
    TIMESTAMP_BEFORE_DURATION,
    UNIQUE,
    URL_PRESENT,
}
