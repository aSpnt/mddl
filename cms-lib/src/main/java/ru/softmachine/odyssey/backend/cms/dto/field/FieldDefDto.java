package ru.softmachine.odyssey.backend.cms.dto.field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.dto.DictionaryExternalShortDto;
import ru.softmachine.odyssey.backend.cms.dto.FieldValidationDto;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueTransitionDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRefWithCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class FieldDefDto extends BaseFieldDefDto {

    private String expression;

    private String defaultTextValue;

    private String exampleText;

    private Boolean defaultBooleanValue;

    private Double defaultDoubleValue;

    private Long defaultIntValue;

    private Boolean setCurrentDateAsDefault;

    private BaseRef defaultRefValue;

    private List<String> defaultRefFilterValues;

    private List<String> predefinedSelections;

    private Boolean canChangeOrder;

    private Boolean compressionEnabled;

    private Integer compressionLimit;

    private Boolean forSlugGenerator;

    private Boolean required;

    private Boolean createDefault;

    private Character ftsPriority;

    private String ftsLanguage;

    private BaseRefWithCode collectionRef;

    private DictionaryExternalShortDto dictionaryExternal;

    private List<FieldValidationDto> fieldValidations;

    private List<FieldValueTransitionDto> fieldTransitions;
}
