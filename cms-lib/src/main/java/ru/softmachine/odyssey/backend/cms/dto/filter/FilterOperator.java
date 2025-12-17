package ru.softmachine.odyssey.backend.cms.dto.filter;

public enum FilterOperator {

    EQUAL,
    GT,
    GTE,
    LT,
    LTE,
    LIKE,
    ILIKE, // unsupported now
    IN,
    INTERSECT,
}
