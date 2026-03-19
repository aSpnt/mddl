package com.aspnt.mddl.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@JsonPropertyOrder({"code", "descending"})
public class ExternalApiFieldOrder {
    public static final String JSON_PROPERTY_CODE = "code";
    @Nonnull
    private String code;
    public static final String JSON_PROPERTY_DESCENDING = "descending";
    @Nullable
    private Boolean descending;

    public ExternalApiFieldOrder code(@Nonnull String code) {
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

    public ExternalApiFieldOrder descending(@Nullable Boolean descending) {
        this.descending = descending;
        return this;
    }

    @Nullable
    @JsonProperty("descending")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public Boolean getDescending() {
        return this.descending;
    }

    @JsonProperty("descending")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setDescending(@Nullable Boolean descending) {
        this.descending = descending;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ExternalApiFieldOrder fieldOrder = (ExternalApiFieldOrder)o;
            return Objects.equals(this.code, fieldOrder.code) && Objects.equals(this.descending, fieldOrder.descending);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.code, this.descending);
    }

    public String toString() {
        return "class ExternalApiFieldOrder {\n" +
                "    code: " + this.toIndentedString(this.code) + "\n" +
                "    descending: " + this.toIndentedString(this.descending) + "\n" +
                "}";
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
