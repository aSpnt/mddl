package ru.softmachine.odyssey.backend.cms.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.softmachine.odyssey.backend.cms.config.ConverterConfig;
import ru.softmachine.odyssey.backend.cms.converter.base.BaseConverter;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.FieldGeometryValueDto;
import ru.softmachine.odyssey.backend.cms.entity.FieldGeometryValue;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
                GeometryConverter.class,
        }
)
public abstract class FieldGeometryValueConverter implements BaseConverter<FieldGeometryValue, FieldGeometryValueDto> {

        public static final String FIELD_GEOMETRY_COPY = "FIELD_GEOMETRY_COPY";

        @Named(FIELD_GEOMETRY_COPY)
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "createdTs", ignore = true)
        @Mapping(target = "updatedTs", ignore = true)
        public abstract FieldGeometryValueDto mapWithoutId(FieldGeometryValue dto);
}
