package com.aspnt.mddl.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonPropertyOrder({"operator", "fieldFilter"})
public class ExternalApiFieldFilterBlock {

    public static final String JSON_PROPERTY_OPERATOR = "operator";
    @Nonnull
    private ExternalApiGlobalFilterOperation operator;
    public static final String JSON_PROPERTY_FIELD_FILTER = "fieldFilter";
    @Nullable
    private List<@Valid ExternalApiFieldFilter> fieldFilter;

    public ExternalApiFieldFilterBlock operator(@Nonnull ExternalApiGlobalFilterOperation operator) {
        this.operator = operator;
        return this;
    }

    @Nonnull
    @JsonProperty("operator")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public @NotNull @Valid ExternalApiGlobalFilterOperation getOperator() {
        return this.operator;
    }

    @JsonProperty("operator")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public void setOperator(@Nonnull ExternalApiGlobalFilterOperation operator) {
        this.operator = operator;
    }

    public ExternalApiFieldFilterBlock fieldFilter(@Nullable List<@Valid ExternalApiFieldFilter> fieldFilter) {
        this.fieldFilter = fieldFilter;
        return this;
    }

    public ExternalApiFieldFilterBlock addFieldFilterItem(ExternalApiFieldFilter fieldFilterItem) {
        if (this.fieldFilter == null) {
            this.fieldFilter = new ArrayList();
        }

        this.fieldFilter.add(fieldFilterItem);
        return this;
    }

    @Nullable
    @JsonProperty("fieldFilter")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public @Valid List<@Valid ExternalApiFieldFilter> getFieldFilter() {
        return this.fieldFilter;
    }

    @JsonProperty("fieldFilter")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setFieldFilter(@Nullable List<@Valid ExternalApiFieldFilter> fieldFilter) {
        this.fieldFilter = fieldFilter;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ExternalApiFieldFilterBlock fieldFilterBlock = (ExternalApiFieldFilterBlock) o;
            return Objects.equals(this.operator, fieldFilterBlock.operator) && Objects.equals(this.fieldFilter, fieldFilterBlock.fieldFilter);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.operator, this.fieldFilter);
    }

    public String toString() {
        return "class ExternalApiFieldFilterBlock {\n" +
                "    operator: " + this.toIndentedString(this.operator) + "\n" +
                "    fieldFilter: " + this.toIndentedString(this.fieldFilter) + "\n" +
                "}";
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
