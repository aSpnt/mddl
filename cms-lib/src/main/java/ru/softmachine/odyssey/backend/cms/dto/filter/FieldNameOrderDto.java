package ru.softmachine.odyssey.backend.cms.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FieldNameOrderDto {

    private String fieldName;
    private boolean descending;
}
