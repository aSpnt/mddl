package ru.softmachine.odyssey.backend.cms.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Позволяет формировать отдельные блоки с различными
 * логическими операторами объединения
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FieldFilterBlockDto {

    private List<FieldFilterDto> fieldFilters;

    private FilterGlobalOperator operator = FilterGlobalOperator.AND;
}
