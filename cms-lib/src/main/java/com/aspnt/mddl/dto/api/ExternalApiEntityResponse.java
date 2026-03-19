package com.aspnt.mddl.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@JsonPropertyOrder({"items", "totalCount"})
public class ExternalApiEntityResponse {
    public static final String JSON_PROPERTY_ITEMS = "items";
    @Nonnull
    private List<Map<String, Object>> items;
    public static final String JSON_PROPERTY_TOTAL_COUNT = "totalCount";
    @Nonnull
    private Long totalCount;

    public ExternalApiEntityResponse items(@Nonnull List<Map<String, Object>> items) {
        this.items = items;
        return this;
    }

    public ExternalApiEntityResponse addItemsItem(Map<String, Object> itemsItem) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }

        this.items.add(itemsItem);
        return this;
    }

    @Nonnull
    @JsonProperty("items")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public @NotNull @Valid List<Map<String, Object>> getItems() {
        return this.items;
    }

    @JsonProperty("items")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public void setItems(@Nonnull List<Map<String, Object>> items) {
        this.items = items;
    }

    public ExternalApiEntityResponse totalCount(@Nonnull Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    @Nonnull
    @JsonProperty("totalCount")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public @NotNull Long getTotalCount() {
        return this.totalCount;
    }

    @JsonProperty("totalCount")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public void setTotalCount(@Nonnull Long totalCount) {
        this.totalCount = totalCount;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ExternalApiEntityResponse entityResponse = (ExternalApiEntityResponse) o;
            return Objects.equals(this.items, entityResponse.items) && Objects.equals(this.totalCount, entityResponse.totalCount);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.items, this.totalCount);
    }

    public String toString() {
        return "class ExternalApiEntityResponse {\n" +
                "    items: " + this.toIndentedString(this.items) + "\n" +
                "    totalCount: " + this.toIndentedString(this.totalCount) + "\n" +
                "}";
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
