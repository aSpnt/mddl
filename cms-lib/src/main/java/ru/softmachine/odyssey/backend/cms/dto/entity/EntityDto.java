package ru.softmachine.odyssey.backend.cms.dto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseStringDto;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EntityDto extends BaseStringDto {

    private Long version;

    private int seq = 0;

    private ZonedDateTime lastStatusChangeTs;

    private String slug;

    private Boolean slugLock;

    private Boolean deleteLock;

    private Boolean active;

    private BaseRef entityDef;

    private List<FieldValueDto> values = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String author;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String authorEmail;

    private String entityTemplateName;
}
