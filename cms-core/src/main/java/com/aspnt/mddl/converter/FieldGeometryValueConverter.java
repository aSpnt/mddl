package com.aspnt.mddl.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import com.aspnt.mddl.config.ConverterConfig;
import com.aspnt.mddl.converter.base.BaseConverter;
import com.aspnt.mddl.converter.base.ReferenceMapper;
import com.aspnt.mddl.dto.FieldGeometryValueDto;
import com.aspnt.mddl.entity.FieldGeometryValue;

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
