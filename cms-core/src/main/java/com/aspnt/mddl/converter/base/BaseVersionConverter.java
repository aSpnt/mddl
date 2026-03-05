package com.aspnt.mddl.converter.base;

import org.mapstruct.Mapping;
import com.aspnt.mddl.entity.base.BaseEntity;

public interface BaseVersionConverter <E extends BaseEntity, D> extends BaseConverter<E, D> {

    @Override
    @Mapping(target = "createdTs", ignore = true)
    @Mapping(target = "updatedTs", ignore = true)
    @Mapping(target = "version", ignore = true)
    E convertToModel(D dto);
}
