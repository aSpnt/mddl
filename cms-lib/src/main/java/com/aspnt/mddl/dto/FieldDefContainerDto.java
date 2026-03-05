package com.aspnt.mddl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.aspnt.mddl.dto.base.BaseDto;
import com.aspnt.mddl.dto.field.FieldDefDto;
import com.aspnt.mddl.dto.base.FieldDefContainerType;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FieldDefContainerDto extends BaseDto {

    private String name;

    private String code;

    private Integer seq;

    private String visibleCondition;

    private String disableCondition;

    private FieldDefContainerType type;

    private List<FieldDefContainerDto> childContainers;

    private List<FieldDefDto> fields;
}
