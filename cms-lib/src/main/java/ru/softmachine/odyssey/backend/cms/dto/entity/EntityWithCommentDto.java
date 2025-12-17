package ru.softmachine.odyssey.backend.cms.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.softmachine.odyssey.backend.cms.dto.EntityCommentDto;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EntityWithCommentDto extends EntityDto {

    /**
     * Комменарий к созданию/удалению/изменению (обрабатывается отедльно)
     */
    private EntityCommentDto comment;
}
