package com.aspnt.mddl.dto.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ExternalApiGlobalFilterOperation {
    AND("AND"),
    OR("OR");

    private final String value;

    ExternalApiGlobalFilterOperation(String value) {
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
    public static ExternalApiGlobalFilterOperation fromValue(String value) {
        for (ExternalApiGlobalFilterOperation op : values()) {
            if (op.value.equals(value)) {
                return op;
            }
        }

        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
