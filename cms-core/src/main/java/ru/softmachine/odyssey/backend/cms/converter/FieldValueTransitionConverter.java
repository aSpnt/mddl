package ru.softmachine.odyssey.backend.cms.converter;

import org.mapstruct.Mapper;
import ru.softmachine.odyssey.backend.cms.config.ConverterConfig;
import ru.softmachine.odyssey.backend.cms.converter.base.BaseConverter;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueTransitionDto;
import ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
                EntityConverter.class,
        }
)
public abstract class FieldValueTransitionConverter implements
        BaseConverter<FieldValueTransition, FieldValueTransitionDto> {
}
