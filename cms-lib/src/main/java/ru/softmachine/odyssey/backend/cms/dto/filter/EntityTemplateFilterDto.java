package ru.softmachine.odyssey.backend.cms.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.dto.EntityTemplateStatus;

import java.time.ZonedDateTime;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class EntityTemplateFilterDto extends PageFilter {

    private ZonedDateTime createdAtFrom;
    private ZonedDateTime createdAtTo;
    private ZonedDateTime updatedAtFrom;
    private ZonedDateTime updatedAtTo;

    private String searchText;

    private List<EntityTemplateStatus> statuses;

    private FieldNameOrderDto fieldNameOrder;
}
