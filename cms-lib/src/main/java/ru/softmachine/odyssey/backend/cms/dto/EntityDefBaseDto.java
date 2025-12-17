package ru.softmachine.odyssey.backend.cms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.provider.ProviderType;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityDefBaseDto extends BaseDto {

    private Long version;

    @NotNull
    private ProviderType providerType = ProviderType.DEFAULT;

    @NotNull
    private String name;

    private String nominative;

    private String genitive;

    private String templateNote;

    private String successCreateMessage;

    private String successDeleteMessage;

    private String onDeleteConflictMessage;

    private GlobalSearchType globalSearchType;

    @NotNull
    private String code;

    private boolean root;

    private boolean singleton;

    private boolean allowInlineCreation;

    private boolean lockCreating;

    private boolean allowDnd;

    private Boolean showComments;

    private BaseRef entityDefGroup;

    @NotNull
    private EntityDefStatus status;

    private String url;

    private String urlList;

    private DictionaryHttpMethod method;

    private String responseParam;

    private Map<String, Object> defaultBody;

    private String pageFilterName;

    private String pageFilterSizeName;

    private String pageFilterNumberName;
}
