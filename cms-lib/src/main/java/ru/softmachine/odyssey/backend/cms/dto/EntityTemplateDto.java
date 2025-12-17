package ru.softmachine.odyssey.backend.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EntityTemplateDto extends BaseDto {

    private String name;

    private String code;

    private Integer seq;

    private String description;

    private String groupName;

    private EntityTemplateStatus status;

    private BaseRef entityDef;

    private List<FieldValueDto> values;
}
