package com.aspnt.mddl.dto.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ExternalApiFieldFilterOperation {

    EQUAL("EQUAL"),
    GT("GT"),
    GTE("GTE"),
    LT("LT"),
    LTE("LTE"),
    LIKE("LIKE"),
    ILIKE("ILIKE"),
    IN("IN"),
    INTERSECT("INTERSECT");

    private final String value;

    ExternalApiFieldFilterOperation(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    @JsonCreator
    public static ExternalApiFieldFilterOperation fromValue(String value) {
        for (ExternalApiFieldFilterOperation op : values()) {
            if (op.value.equals(value)) {
                return op;
            }
        }

        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
