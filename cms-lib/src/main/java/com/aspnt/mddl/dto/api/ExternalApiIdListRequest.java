package com.aspnt.mddl.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonPropertyOrder({"ids"})
public class ExternalApiIdListRequest {

    public static final String JSON_PROPERTY_IDS = "ids";
    @Nonnull
    private List<String> ids;

    public ExternalApiIdListRequest ids(@Nonnull List<String> ids) {
        this.ids = ids;
        return this;
    }

    public ExternalApiIdListRequest addIdsItem(String idsItem) {
        if (this.ids == null) {
            this.ids = new ArrayList();
        }

        this.ids.add(idsItem);
        return this;
    }

    @Nonnull
    @JsonProperty("ids")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public @NotNull List<String> getIds() {
        return this.ids;
    }

    @JsonProperty("ids")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public void setIds(@Nonnull List<String> ids) {
        this.ids = ids;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ExternalApiIdListRequest idListRequest = (ExternalApiIdListRequest) o;
            return Objects.equals(this.ids, idListRequest.ids);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.ids);
    }

    public String toString() {
        return "class ExternalApiIdListRequest {\n" +
                "    ids: " + this.toIndentedString(this.ids) + "\n" +
                "}";
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
