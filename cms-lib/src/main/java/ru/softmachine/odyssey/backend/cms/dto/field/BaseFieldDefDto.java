package ru.softmachine.odyssey.backend.cms.dto.field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.base.FieldType;
import ru.softmachine.odyssey.backend.cms.dto.base.FieldViewType;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BaseFieldDefDto extends BaseDto {

    private FieldDefType fieldDefType;

    private String name;

    private String code;

    private FieldType type;

    private String note;

    private String placeholder;

    private String suffix;

    private String prefix;

    private Integer seq;

    private Integer tableSeq;

    private Boolean orderInTable;

    private Boolean defaultOrder;

    private String tableWidth;

    private String visibleCondition;

    private String visibleItemCondition;

    private String disableCondition;

    private String resetDependencyFieldCode;

    private String refCollectionFieldCode;

    private List<String> allowedTemplates;

    private FieldViewType viewType;

    private Integer span;

    private Boolean multiple;

    private Boolean allowDuplicates;

    private Boolean disabled;

    private Boolean visibleTable;

    private Boolean compactTableView;

    private Boolean visibleView;

    private Boolean visibleShort;

    private Boolean visibleForm;

    private Boolean visibleListView;

    private Boolean visibleHeader;

    private Boolean visibleTemplate;

    private Boolean hideOnCreate;

    private Boolean useFilter;

    private String externalFilterName;

    private String externalFilterLowBoundaryName;

    private String externalFilterUpBoundaryName;

    private Boolean labelInside;

    private Boolean useSearchFilter;

    private Boolean serializeEnum;

    private Boolean serializeFull;

    private Boolean allowCollectionRestriction;

    private Boolean allowCollectionRemove;

    private BaseRef mode;
}
