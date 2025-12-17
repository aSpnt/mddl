package ru.softmachine.odyssey.backend.cms.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseStringRef;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FieldFilterDto {

    private BaseRef fieldDef;

    private Double doubleValue;

    private Long intValue;

    private Boolean booleanValue;

    private String textValue;

    private List<String> arrayText;

    private List<Integer> intArray;

    private LocalDate dateValue;

    private ZonedDateTime dateTimeValue;

    private List<BaseRef> dictionaryValues;

    private BaseRef refValue;

    private BaseStringRef externalRefValue;

    private List<BaseStringRef> externalDictionaryValues;

    private FilterOperator operator;
}
