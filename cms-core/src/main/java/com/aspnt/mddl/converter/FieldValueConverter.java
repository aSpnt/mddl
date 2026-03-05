package com.aspnt.mddl.converter;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import com.aspnt.mddl.config.ConverterConfig;
import com.aspnt.mddl.converter.base.BaseConverter;
import com.aspnt.mddl.converter.base.ReferenceMapper;
import com.aspnt.mddl.converter.context.EntityMappingContext;
import com.aspnt.mddl.dto.entity.EntityDto;
import com.aspnt.mddl.dto.FieldValueDto;
import com.aspnt.mddl.entity.Entity;
import com.aspnt.mddl.entity.ExternalDictionaryValue;
import com.aspnt.mddl.entity.FieldValue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
                CommonBaseRefConverter.class,
                EntityConverter.class,
                DictionaryExternalValueConverter.class,
                FieldGeometryValueConverter.class,
        }
)
public abstract class FieldValueConverter implements BaseConverter<FieldValue, FieldValueDto> {

    public static final String ENTITY_TO_DTO_FULL = "ENTITY_TO_DTO_FULL";

    @Autowired
    private EntityConverter entityConverter;
    @Autowired
    private DictionaryExternalValueConverter dictionaryExternalValueConverter;

    @Override
    @Mapping(target = "refValue", qualifiedByName = EntityConverter.BASE_ENTITY_REF_MAPPER)
    @Mapping(target = "fieldDef", qualifiedByName = CommonBaseRefConverter.BASE_FIELD_DEF_REF_MAPPER)
    public abstract FieldValue convertToModel(FieldValueDto dto);

    @Override
    @Mapping(target = "fieldDef", qualifiedByName = CommonBaseRefConverter.FIELD_DEF_TO_BASE_MAPPER)
    @Mapping(target = "entityValue", qualifiedByName = ENTITY_TO_DTO_FULL)
    public abstract FieldValueDto convertToDto(FieldValue entity);

    @AfterMapping
    protected void mapFieldValueFields(@MappingTarget FieldValue entity) {
        if (entity.getExternalValues() != null) {
            entity.getExternalValues().forEach(value -> value.setFieldValue(entity));
        }
        if (entity.getGeometryValues() != null) {
            entity.getGeometryValues().forEach(value -> value.setFieldValue(entity));
        }
    }

    @AfterMapping
    protected void sortFieldValueDtoFields(@MappingTarget FieldValueDto entity) {
        if (entity.getEntities() != null) {
            entity.getEntities().sort(Comparator.comparing(EntityDto::getSeq));
        }
    }


    /**
     * Необходимо контролировать глубину преобразования сущности
     *
     * @param entity
     * @return
     */
    @Named(ENTITY_TO_DTO_FULL)
    protected EntityDto entityToDtoWithFullView(Entity entity) {
        return entityConverter.convertToDtoWithContext(entity, new EntityMappingContext(true));
    }

    /**
     * Необходимо контролировать глубину преобразования коллекции
     *
     * @param list
     * @return
     */
    protected List<EntityDto> entityListToEntityDtoList(List<Entity> list) {
        if (list == null) {
            return null;
        }
        List<EntityDto> list1 = new ArrayList<EntityDto>(list.size());
        list.forEach(entity -> {
            list1.add(entityConverter.convertToDtoWithContext(entity, new EntityMappingContext(true)));
        });
        return list1;
    }

    @AfterMapping
    protected void filterDuplicateExternalValues(@MappingTarget FieldValue fieldValue) {
        if (CollectionUtils.isEmpty(fieldValue.getExternalValues()) || fieldValue.getFieldDef().isAllowDuplicates()) {
            return;
        }
        fieldValue.setExternalValues(
                fieldValue.getExternalValues().stream()
                        .collect(Collectors.groupingBy(ExternalDictionaryValue::getId))
                        .values()
                        .stream()
                        .flatMap(values -> values.stream().limit(1))
                        .sorted(Comparator.comparing(ExternalDictionaryValue::getSeq))
                        .toList()
        );
    }

}
