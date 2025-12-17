package ru.softmachine.odyssey.backend.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseExternalRef;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;
import ru.softmachine.odyssey.backend.cms.dto.field.BaseFieldDefDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class FieldValueDto extends BaseDto {

    // TODO: BaseRef
    private BaseFieldDefDto fieldDef;

    private Boolean booleanValue;

    private Double doubleValue;

    private Long intValue;

    private String textValue;

    private LocalDate dateValue;

    private LocalTime timeValue;

    private ZonedDateTime datetimeValue;

    private EntityDto refValue;

    private Boolean visibleOverride;

    private Boolean disabledOverride;

    private Boolean requiredOverride;

    private Integer seqOverride;

    private List<String> allowTemplatesOverride;

    private List<String> predefinedSelectionsOverride;

    /**
     * Ссылки на существующие самостоятельные сущности
     */
    private List<BaseRef> refValueCollection;

    private List<String> arrayText;

    private List<LocalDate> arrayDate;

    private List<ZonedDateTime> arrayDateTime;

    /**
     * Вложенная сущность
     */
    private EntityDto entityValue;

    /**
     * Вложенные сущности
     */
    private List<EntityDto> entities;

    private List<FieldGeometryValueDto> geometryValues;

    private List<BaseExternalRef> externalValues;

    private BaseExternalRef externalRefValue;
}
