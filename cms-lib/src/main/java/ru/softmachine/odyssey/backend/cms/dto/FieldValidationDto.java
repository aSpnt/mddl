package ru.softmachine.odyssey.backend.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseDto;
import ru.softmachine.odyssey.backend.cms.dto.validation.ValidationType;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldValidationDto extends BaseDto {

    private ValidationType type;

    private String textValue;

    private Double doubleValue;

    private Integer intValue;

    private List<String> textArrayValue;

    private String message;
}
