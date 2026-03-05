package com.aspnt.mddl.converter;

import org.mapstruct.Mapper;
import com.aspnt.mddl.config.ConverterConfig;
import com.aspnt.mddl.converter.base.BaseConverter;
import com.aspnt.mddl.converter.base.ReferenceMapper;
import com.aspnt.mddl.dto.FieldValidationDto;
import com.aspnt.mddl.entity.validation.FieldValidation;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
        }
)
public abstract class FieldValidationConverter implements BaseConverter<FieldValidation, FieldValidationDto> {
}
