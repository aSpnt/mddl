package com.aspnt.mddl.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonPropertyOrder({"identifiedBy", "values"})
public class ExternalApiFieldArrayValueRef {
    public static final String JSON_PROPERTY_IDENTIFIED_BY = "identifiedBy";
    @Nullable
    private String identifiedBy;
    public static final String JSON_PROPERTY_VALUES = "values";
    @Nullable
    private List<String> values;

    public ExternalApiFieldArrayValueRef identifiedBy(@Nullable String identifiedBy) {
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

    public ExternalApiFieldArrayValueRef values(@Nullable List<String> values) {
        this.values = values;
        return this;
    }

    public ExternalApiFieldArrayValueRef addValuesItem(String valuesItem) {
        if (this.values == null) {
            this.values = new ArrayList();
        }

        this.values.add(valuesItem);
        return this;
    }

    @Nullable
    @JsonProperty("values")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public List<String> getValues() {
        return this.values;
    }

    @JsonProperty("values")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setValues(@Nullable List<String> values) {
        this.values = values;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ExternalApiFieldArrayValueRef fieldArrayValueRef = (ExternalApiFieldArrayValueRef) o;
            return Objects.equals(this.identifiedBy, fieldArrayValueRef.identifiedBy) && Objects.equals(this.values, fieldArrayValueRef.values);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.identifiedBy, this.values);
    }

    public String toString() {
        return "class ExternalApiFieldArrayValueRef {\n" +
                "    identifiedBy: " + this.toIndentedString(this.identifiedBy) + "\n" +
                "    values: " + this.toIndentedString(this.values) + "\n" +
                "}";
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
