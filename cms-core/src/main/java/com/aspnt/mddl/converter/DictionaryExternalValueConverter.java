package com.aspnt.mddl.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import com.aspnt.mddl.config.ConverterConfig;
import com.aspnt.mddl.converter.base.ReferenceMapper;
import com.aspnt.mddl.dto.base.BaseExternalRef;
import com.aspnt.mddl.entity.ExternalDictionaryValue;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
        }
)
public abstract class DictionaryExternalValueConverter {

    public abstract BaseExternalRef convertToDto(ExternalDictionaryValue entity);

    @Mapping(target = "createdTs", ignore = true)
    @Mapping(target = "updatedTs", ignore = true)
    public abstract ExternalDictionaryValue convertToModel(BaseExternalRef dto);

    public static final String DICTIONARY_EXTERNAL_COPY = "DICTIONARY_EXTERNAL_COPY";

    @Named(DICTIONARY_EXTERNAL_COPY)
    @Mapping(target = "id", ignore = true)
    public abstract BaseExternalRef mapWithoutId(ExternalDictionaryValue dto);
}
