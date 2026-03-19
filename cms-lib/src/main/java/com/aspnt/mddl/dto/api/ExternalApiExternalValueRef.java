package com.aspnt.mddl.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@JsonPropertyOrder({"id", "name"})
public class ExternalApiExternalValueRef {
    public static final String JSON_PROPERTY_ID = "id";
    @Nonnull
    private String id;
    public static final String JSON_PROPERTY_NAME = "name";
    @Nullable
    private String name;

    public ExternalApiExternalValueRef id(@Nonnull String id) {
        this.id = id;
        return this;
    }

    @Nonnull
    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public @NotNull String getId() {
        return this.id;
    }

    @JsonProperty("id")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public void setId(@Nonnull String id) {
        this.id = id;
    }

    public ExternalApiExternalValueRef name(@Nullable String name) {
        this.name = name;
        return this;
    }

    @Nullable
    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public String getName() {
        return this.name;
    }

    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setName(@Nullable String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ExternalApiExternalValueRef externalValueRef = (ExternalApiExternalValueRef) o;
            return Objects.equals(this.id, externalValueRef.id) && Objects.equals(this.name, externalValueRef.name);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id, this.name});
    }

    public String toString() {
        return "class ExternalApiExternalValueRef {\n" +
                "    id: " + this.toIndentedString(this.id) + "\n" +
                "    name: " + this.toIndentedString(this.name) + "\n" +
                "}";
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
