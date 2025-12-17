package ru.softmachine.odyssey.backend.cms.converter;

import org.mapstruct.Mapper;
import ru.softmachine.odyssey.backend.cms.config.ConverterConfig;
import ru.softmachine.odyssey.backend.cms.converter.base.BaseConverter;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.FieldValidationDto;
import ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
        }
)
public abstract class FieldValidationConverter implements BaseConverter<FieldValidation, FieldValidationDto> {
}
