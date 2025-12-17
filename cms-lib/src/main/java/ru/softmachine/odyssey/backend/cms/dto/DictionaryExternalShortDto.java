package ru.softmachine.odyssey.backend.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryExternalShortDto extends BaseRef {

    private BaseRef entityDef;
}
