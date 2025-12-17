package ru.softmachine.odyssey.backend.cms.dto.base;

public enum FieldType {
    STRING,
    TEXT,
    STATUS,
    SLUG,
    BOOLEAN,
    DOUBLE,
    INT,
    SEQ,
    DATETIME,
    DATE,
    TIME,
    IMAGE,
    VIDEO,
    COLOR,
    LABEL,
    HR,
    DICTIONARY, // ссылка на сущность
    DICTIONARY_EXTERNAL,
    EXTERNAL_VALUE,
    ENTITY, // вложенная сущность, сохраняемая каскадно
    COLLECTION,
    COLLECTION_FILTERED, // производная некоторой коллекции полученная фильтром по boolean SPEL выражению
    URL,
    DISCLAIMER,
    TAGS,
    JSON,
    HTML,
    HTML_TEMPLATE,
    LINK,
    MAP,
    STAR,
    EXPRESSION;
}
