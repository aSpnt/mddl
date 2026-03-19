package com.aspnt.mddl.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonPropertyOrder({"slugs"})
public class ExternalApiSlugListRequest {
    public static final String JSON_PROPERTY_SLUGS = "slugs";
    @Nonnull
    private List<String> slugs;

    public ExternalApiSlugListRequest slugs(@Nonnull List<String> slugs) {
        this.slugs = slugs;
        return this;
    }

    public ExternalApiSlugListRequest addSlugsItem(String slugsItem) {
        if (this.slugs == null) {
            this.slugs = new ArrayList();
        }

        this.slugs.add(slugsItem);
        return this;
    }

    @Nonnull
    @JsonProperty("slugs")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public @NotNull List<String> getSlugs() {
        return this.slugs;
    }

    @JsonProperty("slugs")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public void setSlugs(@Nonnull List<String> slugs) {
        this.slugs = slugs;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ExternalApiSlugListRequest slugListRequest = (ExternalApiSlugListRequest) o;
            return Objects.equals(this.slugs, slugListRequest.slugs);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.slugs);
    }

    public String toString() {
        return "class ExternalApiSlugListRequest {\n" +
                "    slugs: " + this.toIndentedString(this.slugs) + "\n" +
                "}";
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
