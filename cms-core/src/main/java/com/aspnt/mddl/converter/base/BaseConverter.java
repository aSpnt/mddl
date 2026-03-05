package com.aspnt.mddl.converter.base;

import org.mapstruct.Mapping;
import com.aspnt.mddl.dto.base.BaseRef;
import com.aspnt.mddl.entity.base.BaseEntity;

public interface BaseConverter<E extends BaseEntity, D> {

    D convertToDto(E entity);

    BaseRef convertToBaseRef(E entity);

    @Mapping(target = "createdTs", ignore = true)
    @Mapping(target = "updatedTs", ignore = true)
    E convertToModel(D dto);
}
