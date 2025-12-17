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
public class FieldValueTransitionDto extends BaseDto {

    private BaseRef entityFrom;

    private BaseRef entityTo;

    private Boolean showButton;

    private Boolean updateLastStatusTs;

    private Boolean entityActiveStatus;

    private String message;

    private int seq;

    private Boolean needComment;

    private Boolean commentRequired;

    private String commentTitle;

    private String commentNote;

    private List<String> fieldCodes;
}
