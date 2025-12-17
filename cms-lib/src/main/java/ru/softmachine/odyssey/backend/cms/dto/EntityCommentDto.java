package ru.softmachine.odyssey.backend.cms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EntityCommentDto extends BaseDto {

    private String title;

    private String message;

    private BaseRef entity;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String author;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String authorEmail;
}
