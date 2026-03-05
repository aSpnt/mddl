package com.aspnt.mddl.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.aspnt.mddl.config.ConverterConfig;
import com.aspnt.mddl.converter.base.ReferenceMapper;
import com.aspnt.mddl.dto.entity.EntityDto;
import com.aspnt.mddl.dto.FieldValueDto;
import com.aspnt.mddl.entity.Entity;
import com.aspnt.mddl.entity.FieldValue;

import java.util.List;

import static com.aspnt.mddl.converter.FieldGeometryValueConverter.FIELD_GEOMETRY_COPY;
import static com.aspnt.mddl.converter.EntityConverter.ENTITY_COPY;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
                EntityConverter.class,
                FieldGeometryValueConverter.class,
                DictionaryExternalValueConverter.class,
        }
)
public abstract class FieldValueUtilsConverter {

    public static final String FIELD_VALUE_COPY = "FIELD_VALUE_COPY";

    @Autowired
    private EntityUtilsConverter entityUtilsConverter;

    protected List<EntityDto> entityListToEntityDtoList(List<Entity> list) {
        if (list == null) {
            return null;
        }

        return list.stream()
                .map(entity -> {
                    var entityDto = entityUtilsConverter.mapWithoutIdAndValues(entity);
                    entityDto.setValues(entity.getValues().stream()
                            .map(this::mapWithoutId)
                            .toList()
                    );
                    return entityDto;
                })
                .toList();
    }

    @Named(FIELD_VALUE_COPY)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTs", ignore = true)
    @Mapping(target = "updatedTs", ignore = true)
    //@Mapping(target = "externalValues", qualifiedByName = DICTIONARY_EXTERNAL_COPY)
    @Mapping(target = "geometryValues", qualifiedByName = FIELD_GEOMETRY_COPY)
    @Mapping(target = "entityValue", qualifiedByName = ENTITY_COPY)
    @Mapping(target = "entities", qualifiedByName = ENTITY_COPY)
    public abstract FieldValueDto mapWithoutId(FieldValue dto);
}
