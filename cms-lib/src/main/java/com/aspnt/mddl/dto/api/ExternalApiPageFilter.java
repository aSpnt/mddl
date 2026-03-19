package com.aspnt.mddl.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Setter
public class ExternalApiPageFilter {
    @Nullable
    private Integer pageSize;
    @Nullable
    private Integer pageIndex;

    public ExternalApiPageFilter pageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @javax.annotation.Nullable
    @JsonProperty("pageSize")
    public Integer getPageSize() {
        return this.pageSize;
    }

    public ExternalApiPageFilter pageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    @javax.annotation.Nullable
    @JsonProperty("pageIndex")
    public Integer getPageIndex() {
        return this.pageIndex;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ExternalApiPageFilter ExternalApiPageFilter = (ExternalApiPageFilter)o;
            return Objects.equals(this.pageSize, ExternalApiPageFilter.pageSize) && Objects.equals(this.pageIndex, ExternalApiPageFilter.pageIndex);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.pageSize, this.pageIndex);
    }

    public String toString() {
        return "class ExternalApiPageFilter {\n" +
                "    pageSize: " + this.toIndentedString(this.pageSize) + "\n" +
                "    pageIndex: " + this.toIndentedString(this.pageIndex) + "\n" +
                "}";
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
