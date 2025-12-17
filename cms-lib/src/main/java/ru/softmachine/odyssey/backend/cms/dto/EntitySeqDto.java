package ru.softmachine.odyssey.backend.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.dto.base.StringIdentDto;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EntitySeqDto extends StringIdentDto {

    private int seq;
}
