package ru.softmachine.odyssey.backend.cms.converter.base;

import org.mapstruct.Mapping;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity;

public interface BaseConverter<E extends BaseEntity, D> {

    D convertToDto(E entity);

    BaseRef convertToBaseRef(E entity);

    @Mapping(target = "createdTs", ignore = true)
    @Mapping(target = "updatedTs", ignore = true)
    E convertToModel(D dto);
}
