package ru.softmachine.odyssey.backend.cms.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EntityFilterDto extends PageFilter {

    private List<FieldFilterDto> fieldFilters;
    private List<FieldFilterBlockDto> fieldFilterBlocks;
    private List<FieldFilterDto> fixedFieldFilters;
    private SearchFilterDto searchFilter;
    private FilterGlobalOperator globalOperator = FilterGlobalOperator.AND;

    // TODO: временное упрощение
    private FieldOrderDto fieldOrder;
    private FieldOrderDto fixedFieldOrder;

    private boolean activeOnly;
}
