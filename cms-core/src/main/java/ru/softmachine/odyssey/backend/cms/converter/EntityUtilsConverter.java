package ru.softmachine.odyssey.backend.cms.converter;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.softmachine.odyssey.backend.cms.config.ConverterConfig;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;
import ru.softmachine.odyssey.backend.cms.entity.Entity;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
                FieldValueConverter.class,
        }
)
@Slf4j
public abstract class EntityUtilsConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTs", ignore = true)
    @Mapping(target = "updatedTs", ignore = true)
    @Mapping(target = "values", ignore = true)
    public abstract EntityDto mapWithoutIdAndValues(Entity dto);
}
