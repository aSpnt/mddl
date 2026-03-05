package com.aspnt.mddl.dto.field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.aspnt.mddl.dto.DictionaryExternalShortDto;
import com.aspnt.mddl.dto.FieldValidationDto;
import com.aspnt.mddl.dto.FieldValueTransitionDto;
import com.aspnt.mddl.dto.base.BaseRef;
import com.aspnt.mddl.dto.base.BaseRefWithCode;

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
