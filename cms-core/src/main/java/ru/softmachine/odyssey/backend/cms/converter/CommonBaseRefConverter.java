package ru.softmachine.odyssey.backend.cms.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.softmachine.odyssey.backend.cms.config.ConverterConfig;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseDto;
import ru.softmachine.odyssey.backend.cms.dto.field.BaseFieldDefDto;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
        }
)
public abstract class CommonBaseRefConverter {

    public static final String FIELD_DEF_TO_BASE_MAPPER = "FIELD_DEF_TO_BASE_MAPPER";
    public static final String BASE_FIELD_DEF_REF_MAPPER = "BASE_FIELD_DEF_REF";

    @Named(FIELD_DEF_TO_BASE_MAPPER)
    public abstract BaseFieldDefDto convertToBaseFieldDef(FieldDef entity);

    @Mapping(target = "createdTs", ignore = true)
    @Mapping(target = "updatedTs", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Named(BASE_FIELD_DEF_REF_MAPPER)
    public abstract FieldDef baseFieldDefMapperByBAseDto(BaseDto ref);
}
