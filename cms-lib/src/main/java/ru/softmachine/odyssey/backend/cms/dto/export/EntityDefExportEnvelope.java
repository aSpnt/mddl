package ru.softmachine.odyssey.backend.cms.dto.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.softmachine.odyssey.backend.cms.dto.EntityDefDto;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;

import java.util.List;

/**
 * Данные необходимые для экспорта и восстановления сущности с ее значениями.
 * На текущем этапе не учитывает смежные дефиниции.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityDefExportEnvelope {

    private EntityDefDto entityDef;
    private List<EntityDto> entities;
}
