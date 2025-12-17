package ru.softmachine.odyssey.backend.cms.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import ru.softmachine.odyssey.backend.cms.config.ConverterConfig;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueDto;
import ru.softmachine.odyssey.backend.cms.entity.Entity;
import ru.softmachine.odyssey.backend.cms.entity.FieldValue;

import java.util.List;

import static ru.softmachine.odyssey.backend.cms.converter.FieldGeometryValueConverter.FIELD_GEOMETRY_COPY;
import static ru.softmachine.odyssey.backend.cms.converter.EntityConverter.ENTITY_COPY;

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
