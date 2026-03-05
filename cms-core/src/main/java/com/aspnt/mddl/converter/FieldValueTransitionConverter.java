package com.aspnt.mddl.converter;

import org.mapstruct.Mapper;
import com.aspnt.mddl.config.ConverterConfig;
import com.aspnt.mddl.converter.base.BaseConverter;
import com.aspnt.mddl.converter.base.ReferenceMapper;
import com.aspnt.mddl.dto.FieldValueTransitionDto;
import com.aspnt.mddl.entity.FieldValueTransition;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
                EntityConverter.class,
        }
)
public abstract class FieldValueTransitionConverter implements
        BaseConverter<FieldValueTransition, FieldValueTransitionDto> {
}
