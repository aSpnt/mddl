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

@JsonPropertyOrder({"entityDefCode", "fieldFilter", "fieldFilterBlock", "globalOperator", "fieldOrder", "searchFilter", "pageFilter"})
public class ExternalApiEntityRequest {
    public static final String JSON_PROPERTY_ENTITY_DEF_CODE = "entityDefCode";
    @Nonnull
    private String entityDefCode;
    public static final String JSON_PROPERTY_FIELD_FILTER = "fieldFilter";
    @Nullable
    private List<@Valid ExternalApiFieldFilter> fieldFilter;
    public static final String JSON_PROPERTY_FIELD_FILTER_BLOCK = "fieldFilterBlock";
    @Nullable
    private List<@Valid ExternalApiFieldFilterBlock> fieldFilterBlock;
    public static final String JSON_PROPERTY_GLOBAL_OPERATOR = "globalOperator";
    @Nullable
    private ExternalApiGlobalFilterOperation globalOperator;
    public static final String JSON_PROPERTY_FIELD_ORDER = "fieldOrder";
    @Nullable
    private ExternalApiFieldOrder fieldOrder;
    public static final String JSON_PROPERTY_SEARCH_FILTER = "searchFilter";
    @Nullable
    private ExternalApiTextSearchFilter searchFilter;
    public static final String JSON_PROPERTY_PAGE_FILTER = "pageFilter";
    @Nullable
    private ExternalApiPageFilter pageFilter;

    public ExternalApiEntityRequest entityDefCode(@Nonnull String entityDefCode) {
        this.entityDefCode = entityDefCode;
        return this;
    }

    @Nonnull
    @JsonProperty("entityDefCode")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public @NotNull String getEntityDefCode() {
        return this.entityDefCode;
    }

    @JsonProperty("entityDefCode")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public void setEntityDefCode(@Nonnull String entityDefCode) {
        this.entityDefCode = entityDefCode;
    }

    public ExternalApiEntityRequest fieldFilter(@Nullable List<@Valid ExternalApiFieldFilter> fieldFilter) {
        this.fieldFilter = fieldFilter;
        return this;
    }

    public ExternalApiEntityRequest addFieldFilterItem(ExternalApiFieldFilter fieldFilterItem) {
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

    public ExternalApiEntityRequest fieldFilterBlock(@Nullable List<@Valid ExternalApiFieldFilterBlock> fieldFilterBlock) {
        this.fieldFilterBlock = fieldFilterBlock;
        return this;
    }

    public ExternalApiEntityRequest addFieldFilterBlockItem(ExternalApiFieldFilterBlock fieldFilterBlockItem) {
        if (this.fieldFilterBlock == null) {
            this.fieldFilterBlock = new ArrayList();
        }

        this.fieldFilterBlock.add(fieldFilterBlockItem);
        return this;
    }

    @Nullable
    @JsonProperty("fieldFilterBlock")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public @Valid List<@Valid ExternalApiFieldFilterBlock> getFieldFilterBlock() {
        return this.fieldFilterBlock;
    }

    @JsonProperty("fieldFilterBlock")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setFieldFilterBlock(@Nullable List<@Valid ExternalApiFieldFilterBlock> fieldFilterBlock) {
        this.fieldFilterBlock = fieldFilterBlock;
    }

    public ExternalApiEntityRequest globalOperator(@Nullable ExternalApiGlobalFilterOperation globalOperator) {
        this.globalOperator = globalOperator;
        return this;
    }

    @Nullable
    @JsonProperty("globalOperator")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public @Valid ExternalApiGlobalFilterOperation getGlobalOperator() {
        return this.globalOperator;
    }

    @JsonProperty("globalOperator")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setGlobalOperator(@Nullable ExternalApiGlobalFilterOperation globalOperator) {
        this.globalOperator = globalOperator;
    }

    public ExternalApiEntityRequest fieldOrder(@Nullable ExternalApiFieldOrder fieldOrder) {
        this.fieldOrder = fieldOrder;
        return this;
    }

    @Nullable
    @JsonProperty("fieldOrder")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public @Valid ExternalApiFieldOrder getFieldOrder() {
        return this.fieldOrder;
    }

    @JsonProperty("fieldOrder")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setFieldOrder(@Nullable ExternalApiFieldOrder fieldOrder) {
        this.fieldOrder = fieldOrder;
    }

    public ExternalApiEntityRequest searchFilter(@Nullable ExternalApiTextSearchFilter searchFilter) {
        this.searchFilter = searchFilter;
        return this;
    }

    @Nullable
    @JsonProperty("searchFilter")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public @Valid ExternalApiTextSearchFilter getSearchFilter() {
        return this.searchFilter;
    }

    @JsonProperty("searchFilter")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setSearchFilter(@Nullable ExternalApiTextSearchFilter searchFilter) {
        this.searchFilter = searchFilter;
    }

    public ExternalApiEntityRequest pageFilter(@Nullable ExternalApiPageFilter pageFilter) {
        this.pageFilter = pageFilter;
        return this;
    }

    @Nullable
    @JsonProperty("pageFilter")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public @Valid ExternalApiPageFilter getPageFilter() {
        return this.pageFilter;
    }

    @JsonProperty("pageFilter")
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    public void setPageFilter(@Nullable ExternalApiPageFilter pageFilter) {
        this.pageFilter = pageFilter;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ExternalApiEntityRequest entityRequest = (ExternalApiEntityRequest) o;
            return Objects.equals(this.entityDefCode, entityRequest.entityDefCode) && Objects.equals(this.fieldFilter, entityRequest.fieldFilter) && Objects.equals(this.fieldFilterBlock, entityRequest.fieldFilterBlock) && Objects.equals(this.globalOperator, entityRequest.globalOperator) && Objects.equals(this.fieldOrder, entityRequest.fieldOrder) && Objects.equals(this.searchFilter, entityRequest.searchFilter) && Objects.equals(this.pageFilter, entityRequest.pageFilter);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.entityDefCode, this.fieldFilter, this.fieldFilterBlock, this.globalOperator, this.fieldOrder, this.searchFilter, this.pageFilter});
    }

    public String toString() {
        return "class ExternalApiEntityRequest {\n" +
                "    entityDefCode: " + this.toIndentedString(this.entityDefCode) + "\n" +
                "    fieldFilter: " + this.toIndentedString(this.fieldFilter) + "\n" +
                "    fieldFilterBlock: " + this.toIndentedString(this.fieldFilterBlock) + "\n" +
                "    globalOperator: " + this.toIndentedString(this.globalOperator) + "\n" +
                "    fieldOrder: " + this.toIndentedString(this.fieldOrder) + "\n" +
                "    searchFilter: " + this.toIndentedString(this.searchFilter) + "\n" +
                "    pageFilter: " + this.toIndentedString(this.pageFilter) + "\n" +
                "}";
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}
