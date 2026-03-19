package com.aspnt.mddl.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nullable;

import java.util.Objects;

@JsonPropertyOrder({"identifiedBy", "value"})
public class ExternalApiFieldValueRef {
    public static final String JSON_PROPERTY_IDENTIFIED_BY = "identifiedBy";
    @Nullable
    private String identifiedBy;
    public static final String JSON_PROPERTY_VALUE = "value";
    @Nullable
    private String value;

    public ExternalApiFieldValueRef identifiedBy(@Nullable String identifiedBy) {
        this.identifiedBy = identifiedBy;
        return this;
    }

    @Nullable
    @JsonProperty("identifiedBy")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public String getIdentifiedBy() {
        return this.identifiedBy;
    }

    @JsonProperty("identifiedBy")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setIdentifiedBy(@Nullable String identifiedBy) {
        this.identifiedBy = identifiedBy;
    }

    public ExternalApiFieldValueRef value(@Nullable String value) {
        this.value = value;
        return this;
    }

    @Nullable
    @JsonProperty("value")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public String getValue() {
        return this.value;
    }

    @JsonProperty("value")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setValue(@Nullable String value) {
        this.value = value;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ExternalApiFieldValueRef fieldValueRef = (ExternalApiFieldValueRef) o;
            return Objects.equals(this.identifiedBy, fieldValueRef.identifiedBy) && Objects.equals(this.value, fieldValueRef.value);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.identifiedBy, this.value});
    }

    public String toString() {
        return "class ExternalApiFieldValueRef {\n" +
                "    identifiedBy: " + this.toIndentedString(this.identifiedBy) + "\n" +
                "    value: " + this.toIndentedString(this.value) + "\n" +
                "}";
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
