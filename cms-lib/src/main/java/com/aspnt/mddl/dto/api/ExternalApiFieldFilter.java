package com.aspnt.mddl.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonPropertyOrder({"code", "operation", "valueRef", "arrayValueRef", "externalValueRef", "arrayExternalValueRef", "intValue", "floatValue", "booleanValue", "textValue", "arrayTextValue", "dateTimeValue"})
public class ExternalApiFieldFilter {

    public static final String JSON_PROPERTY_CODE = "code";
    @Nonnull
    private String code;
    public static final String JSON_PROPERTY_OPERATION = "operation";
    @Nullable
    private ExternalApiFieldFilterOperation operation;
    public static final String JSON_PROPERTY_VALUE_REF = "valueRef";
    @Nullable
    private ExternalApiFieldValueRef valueRef;
    public static final String JSON_PROPERTY_ARRAY_VALUE_REF = "arrayValueRef";
    @Nullable
    private ExternalApiFieldArrayValueRef arrayValueRef;
    public static final String JSON_PROPERTY_EXTERNAL_VALUE_REF = "externalValueRef";
    @Nullable
    private ExternalApiExternalValueRef externalValueRef;
    public static final String JSON_PROPERTY_ARRAY_EXTERNAL_VALUE_REF = "arrayExternalValueRef";
    @Nullable
    private List<@Valid ExternalApiExternalValueRef> arrayExternalValueRef;
    public static final String JSON_PROPERTY_INT_VALUE = "intValue";
    @Nullable
    private BigDecimal intValue;
    public static final String JSON_PROPERTY_FLOAT_VALUE = "floatValue";
    @Nullable
    private Double floatValue;
    public static final String JSON_PROPERTY_BOOLEAN_VALUE = "booleanValue";
    @Nullable
    private Boolean booleanValue;
    public static final String JSON_PROPERTY_TEXT_VALUE = "textValue";
    @Nullable
    private String textValue;
    public static final String JSON_PROPERTY_ARRAY_TEXT_VALUE = "arrayTextValue";
    @Nullable
    private List<String> arrayTextValue;
    public static final String JSON_PROPERTY_DATE_TIME_VALUE = "dateTimeValue";
    @Nullable
    private OffsetDateTime dateTimeValue = null;

    public ExternalApiFieldFilter code(@Nonnull String code) {
        this.code = code;
        return this;
    }

    @Nonnull
    @JsonProperty("code")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public @NotNull String getCode() {
        return this.code;
    }

    @JsonProperty("code")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public void setCode(@Nonnull String code) {
        this.code = code;
    }

    public ExternalApiFieldFilter operation(@Nullable ExternalApiFieldFilterOperation operation) {
        this.operation = operation;
        return this;
    }

    @Nullable
    @JsonProperty("operation")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public @Valid ExternalApiFieldFilterOperation getOperation() {
        return this.operation;
    }

    @JsonProperty("operation")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setOperation(@Nullable ExternalApiFieldFilterOperation operation) {
        this.operation = operation;
    }

    public ExternalApiFieldFilter valueRef(@Nullable ExternalApiFieldValueRef valueRef) {
        this.valueRef = valueRef;
        return this;
    }

    @Nullable
    @JsonProperty("valueRef")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public @Valid ExternalApiFieldValueRef getValueRef() {
        return this.valueRef;
    }

    @JsonProperty("valueRef")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setValueRef(@Nullable ExternalApiFieldValueRef valueRef) {
        this.valueRef = valueRef;
    }

    public ExternalApiFieldFilter arrayValueRef(@Nullable ExternalApiFieldArrayValueRef arrayValueRef) {
        this.arrayValueRef = arrayValueRef;
        return this;
    }

    @Nullable
    @JsonProperty("arrayValueRef")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public @Valid ExternalApiFieldArrayValueRef getArrayValueRef() {
        return this.arrayValueRef;
    }

    @JsonProperty("arrayValueRef")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setArrayValueRef(@Nullable ExternalApiFieldArrayValueRef arrayValueRef) {
        this.arrayValueRef = arrayValueRef;
    }

    public ExternalApiFieldFilter externalValueRef(@Nullable ExternalApiExternalValueRef externalValueRef) {
        this.externalValueRef = externalValueRef;
        return this;
    }

    @Nullable
    @JsonProperty("externalValueRef")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public @Valid ExternalApiExternalValueRef getExternalValueRef() {
        return this.externalValueRef;
    }

    @JsonProperty("externalValueRef")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setExternalValueRef(@Nullable ExternalApiExternalValueRef externalValueRef) {
        this.externalValueRef = externalValueRef;
    }

    public ExternalApiFieldFilter arrayExternalValueRef(@Nullable List<@Valid ExternalApiExternalValueRef> arrayExternalValueRef) {
        this.arrayExternalValueRef = arrayExternalValueRef;
        return this;
    }

    public ExternalApiFieldFilter addArrayExternalValueRefItem(ExternalApiExternalValueRef arrayExternalValueRefItem) {
        if (this.arrayExternalValueRef == null) {
            this.arrayExternalValueRef = new ArrayList<>();
        }

        this.arrayExternalValueRef.add(arrayExternalValueRefItem);
        return this;
    }

    @Nullable
    @JsonProperty("arrayExternalValueRef")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public @Valid List<@Valid ExternalApiExternalValueRef> getArrayExternalValueRef() {
        return this.arrayExternalValueRef;
    }

    @JsonProperty("arrayExternalValueRef")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setArrayExternalValueRef(@Nullable List<@Valid ExternalApiExternalValueRef> arrayExternalValueRef) {
        this.arrayExternalValueRef = arrayExternalValueRef;
    }

    public ExternalApiFieldFilter intValue(@Nullable BigDecimal intValue) {
        this.intValue = intValue;
        return this;
    }

    @Nullable
    @JsonProperty("intValue")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public @Valid BigDecimal getIntValue() {
        return this.intValue;
    }

    @JsonProperty("intValue")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setIntValue(@Nullable BigDecimal intValue) {
        this.intValue = intValue;
    }

    public ExternalApiFieldFilter floatValue(@Nullable Double floatValue) {
        this.floatValue = floatValue;
        return this;
    }

    @Nullable
    @JsonProperty("floatValue")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public Double getFloatValue() {
        return this.floatValue;
    }

    @JsonProperty("floatValue")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setFloatValue(@Nullable Double floatValue) {
        this.floatValue = floatValue;
    }

    public ExternalApiFieldFilter booleanValue(@Nullable Boolean booleanValue) {
        this.booleanValue = booleanValue;
        return this;
    }

    @Nullable
    @JsonProperty("booleanValue")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public Boolean getBooleanValue() {
        return this.booleanValue;
    }

    @JsonProperty("booleanValue")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setBooleanValue(@Nullable Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public ExternalApiFieldFilter textValue(@Nullable String textValue) {
        this.textValue = textValue;
        return this;
    }

    @Nullable
    @JsonProperty("textValue")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public String getTextValue() {
        return this.textValue;
    }

    @JsonProperty("textValue")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setTextValue(@Nullable String textValue) {
        this.textValue = textValue;
    }

    public ExternalApiFieldFilter arrayTextValue(@Nullable List<String> arrayTextValue) {
        this.arrayTextValue = arrayTextValue;
        return this;
    }

    public ExternalApiFieldFilter addArrayTextValueItem(String arrayTextValueItem) {
        if (this.arrayTextValue == null) {
            this.arrayTextValue = new ArrayList();
        }

        this.arrayTextValue.add(arrayTextValueItem);
        return this;
    }

    @Nullable
    @JsonProperty("arrayTextValue")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public List<String> getArrayTextValue() {
        return this.arrayTextValue;
    }

    @JsonProperty("arrayTextValue")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setArrayTextValue(@Nullable List<String> arrayTextValue) {
        this.arrayTextValue = arrayTextValue;
    }

    public ExternalApiFieldFilter dateTimeValue(@Nullable OffsetDateTime dateTimeValue) {
        this.dateTimeValue = dateTimeValue;
        return this;
    }

    @Nullable
    @JsonProperty("dateTimeValue")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public @Valid OffsetDateTime getDateTimeValue() {
        return this.dateTimeValue;
    }

    @JsonProperty("dateTimeValue")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setDateTimeValue(@Nullable OffsetDateTime dateTimeValue) {
        this.dateTimeValue = dateTimeValue;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ExternalApiFieldFilter fieldFilter = (ExternalApiFieldFilter)o;
            return Objects.equals(this.code, fieldFilter.code) && Objects.equals(this.operation, fieldFilter.operation) && Objects.equals(this.valueRef, fieldFilter.valueRef) && Objects.equals(this.arrayValueRef, fieldFilter.arrayValueRef) && Objects.equals(this.externalValueRef, fieldFilter.externalValueRef) && Objects.equals(this.arrayExternalValueRef, fieldFilter.arrayExternalValueRef) && Objects.equals(this.intValue, fieldFilter.intValue) && Objects.equals(this.floatValue, fieldFilter.floatValue) && Objects.equals(this.booleanValue, fieldFilter.booleanValue) && Objects.equals(this.textValue, fieldFilter.textValue) && Objects.equals(this.arrayTextValue, fieldFilter.arrayTextValue) && Objects.equals(this.dateTimeValue, fieldFilter.dateTimeValue);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.code, this.operation, this.valueRef, this.arrayValueRef, this.externalValueRef, this.arrayExternalValueRef, this.intValue, this.floatValue, this.booleanValue, this.textValue, this.arrayTextValue, this.dateTimeValue);
    }

    public String toString() {
        return "class ExternalApiFieldFilter {\n" +
                "    code: " + this.toIndentedString(this.code) + "\n" +
                "    operation: " + this.toIndentedString(this.operation) + "\n" +
                "    valueRef: " + this.toIndentedString(this.valueRef) + "\n" +
                "    arrayValueRef: " + this.toIndentedString(this.arrayValueRef) + "\n" +
                "    externalValueRef: " + this.toIndentedString(this.externalValueRef) + "\n" +
                "    arrayExternalValueRef: " + this.toIndentedString(this.arrayExternalValueRef) + "\n" +
                "    intValue: " + this.toIndentedString(this.intValue) + "\n" +
                "    floatValue: " + this.toIndentedString(this.floatValue) + "\n" +
                "    booleanValue: " + this.toIndentedString(this.booleanValue) + "\n" +
                "    textValue: " + this.toIndentedString(this.textValue) + "\n" +
                "    arrayTextValue: " + this.toIndentedString(this.arrayTextValue) + "\n" +
                "    dateTimeValue: " + this.toIndentedString(this.dateTimeValue) + "\n" +
                "}";
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
