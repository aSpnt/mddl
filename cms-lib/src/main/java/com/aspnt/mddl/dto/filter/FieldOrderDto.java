package com.aspnt.mddl.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.aspnt.mddl.dto.base.BaseRef;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FieldOrderDto {

    private BaseRef fieldDef;

    private boolean descending;
}
