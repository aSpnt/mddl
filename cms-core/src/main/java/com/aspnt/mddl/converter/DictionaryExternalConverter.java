package com.aspnt.mddl.converter;

import org.mapstruct.Mapper;
import com.aspnt.mddl.config.ConverterConfig;
import com.aspnt.mddl.converter.base.BaseConverter;
import com.aspnt.mddl.converter.base.ReferenceMapper;
import com.aspnt.mddl.dto.DictionaryExternalDto;
import com.aspnt.mddl.entity.DictionaryExternal;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
                DictionaryExternalHeaderConverter.class,
        }
)
public abstract class DictionaryExternalConverter
        implements BaseConverter<DictionaryExternal, DictionaryExternalDto> {
}
