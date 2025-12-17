package ru.softmachine.odyssey.backend.cms.converter;

import org.mapstruct.Mapper;
import ru.softmachine.odyssey.backend.cms.config.ConverterConfig;
import ru.softmachine.odyssey.backend.cms.converter.base.BaseConverter;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.DictionaryExternalDto;
import ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
                DictionaryExternalHeaderConverter.class,
        }
)
public abstract class DictionaryExternalConverter
        implements BaseConverter<DictionaryExternal, DictionaryExternalDto> {
}
