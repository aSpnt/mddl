package com.aspnt.mddl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.aspnt.mddl.dto.base.BaseRef;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryExternalShortDto extends BaseRef {

    private BaseRef entityDef;
}
