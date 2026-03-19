package com.aspnt.mddl.dto.api;

import com.aspnt.mddl.dto.base.BaseSlugRef;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonPropertyOrder({"items"})
public class ExternalApiListBaseEntity {
    public static final String JSON_PROPERTY_ITEMS = "items";
    @Nonnull
    private List<@Valid BaseSlugRef> items;

    public ExternalApiListBaseEntity items(@Nonnull List<@Valid BaseSlugRef> items) {
        this.items = items;
        return this;
    }

    public ExternalApiListBaseEntity addItemsItem(BaseSlugRef itemsItem) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }

        this.items.add(itemsItem);
        return this;
    }

    @Nonnull
    @JsonProperty("items")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public @NotNull @Valid List<@Valid BaseSlugRef> getItems() {
        return this.items;
    }

    @JsonProperty("items")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public void setItems(@Nonnull List<@Valid BaseSlugRef> items) {
        this.items = items;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ExternalApiListBaseEntity listBaseEntity = (ExternalApiListBaseEntity) o;
            return Objects.equals(this.items, listBaseEntity.items);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.items);
    }

    public String toString() {
        return "class ExternalApiListBaseEntity {\n" +
                "    items: " + this.toIndentedString(this.items) + "\n" +
                "}";
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
