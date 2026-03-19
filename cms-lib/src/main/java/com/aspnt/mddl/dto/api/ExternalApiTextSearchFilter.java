package com.aspnt.mddl.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nullable;

import java.util.Objects;

@JsonPropertyOrder({"search", "ftsSearch"})
public class ExternalApiTextSearchFilter {
    public static final String JSON_PROPERTY_SEARCH = "search";
    @Nullable
    private String search;
    public static final String JSON_PROPERTY_FTS_SEARCH = "ftsSearch";
    @Nullable
    private String ftsSearch;

    public ExternalApiTextSearchFilter search(@Nullable String search) {
        this.search = search;
        return this;
    }

    @Nullable
    @JsonProperty("search")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public String getSearch() {
        return this.search;
    }

    @JsonProperty("search")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setSearch(@Nullable String search) {
        this.search = search;
    }

    public ExternalApiTextSearchFilter ftsSearch(@Nullable String ftsSearch) {
        this.ftsSearch = ftsSearch;
        return this;
    }

    @Nullable
    @JsonProperty("ftsSearch")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public String getFtsSearch() {
        return this.ftsSearch;
    }

    @JsonProperty("ftsSearch")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setFtsSearch(@Nullable String ftsSearch) {
        this.ftsSearch = ftsSearch;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ExternalApiTextSearchFilter textSearchFilter = (ExternalApiTextSearchFilter) o;
            return Objects.equals(this.search, textSearchFilter.search) && Objects.equals(this.ftsSearch, textSearchFilter.ftsSearch);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.search, this.ftsSearch);
    }

    public String toString() {
        return "class ExternalApiTextSearchFilter {\n" +
                "    search: " + this.toIndentedString(this.search) + "\n" +
                "    ftsSearch: " + this.toIndentedString(this.ftsSearch) + "\n" +
                "}";
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
