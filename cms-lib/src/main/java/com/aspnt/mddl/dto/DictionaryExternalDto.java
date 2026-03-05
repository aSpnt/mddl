package com.aspnt.mddl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.aspnt.mddl.dto.base.BaseDto;
import com.aspnt.mddl.dto.base.BaseRef;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryExternalDto extends BaseDto {

    private String name;

    private String code;

    private String url;

    private List<String> param;

    private DictionaryHttpMethod method;

    private String responseParam;

    private Map<String, Object> defaultBody;

    private List<DictionaryExternalHeaderDto> headers;

    private BaseRef entityDef;
}
