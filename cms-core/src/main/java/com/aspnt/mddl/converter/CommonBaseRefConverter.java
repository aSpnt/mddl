package com.aspnt.mddl.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import com.aspnt.mddl.config.ConverterConfig;
import com.aspnt.mddl.converter.base.ReferenceMapper;
import com.aspnt.mddl.dto.base.BaseDto;
import com.aspnt.mddl.dto.base.BaseRef;
import com.aspnt.mddl.dto.field.BaseFieldDefDto;
import com.aspnt.mddl.entity.entitydef.EntityDef;
import com.aspnt.mddl.entity.field.FieldDef;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
        }
)
public abstract class CommonBaseRefConverter {

    public static final String FIELD_DEF_TO_BASE_MAPPER = "FIELD_DEF_TO_BASE_MAPPER";
    public static final String BASE_FIELD_DEF_REF_MAPPER = "BASE_FIELD_DEF_REF";
    public static final String BASE_ENTITY_DEF_REF_MAPPER = "BASE_ENTITY_DEF_REF_MAPPER";

    @Named(FIELD_DEF_TO_BASE_MAPPER)
    public abstract BaseFieldDefDto convertToBaseFieldDef(FieldDef entity);

    @Mapping(target = "createdTs", ignore = true)
    @Mapping(target = "updatedTs", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Named(BASE_FIELD_DEF_REF_MAPPER)
    public abstract FieldDef baseFieldDefMapperByBaseDto(BaseDto ref);

    @Mapping(target = "name", ignore = true)
    abstract EntityDef baseRefToEntityDef(BaseRef baseRef);
}
