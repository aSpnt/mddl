package ru.softmachine.odyssey.backend.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EntityDefDto extends EntityDefBaseDto {

    private EntityDto singletonEntity;

    private FieldDefContainerDto container;
}
