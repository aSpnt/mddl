package ru.softmachine.odyssey.backend.cms.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FieldOrderDto {

    private BaseRef fieldDef;

    private boolean descending;
}
