package ru.softmachine.odyssey.backend.cms.converter;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.softmachine.odyssey.backend.cms.config.ConverterConfig;
import ru.softmachine.odyssey.backend.cms.converter.base.BaseConverter;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.EntityTemplateDto;
import ru.softmachine.odyssey.backend.cms.entity.EntityTemplate;

import java.util.HashMap;
import java.util.Map;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
                FieldValueConverter.class,
        }
)
@Slf4j
public abstract class EntityTemplateConverter implements BaseConverter<EntityTemplate, EntityTemplateDto> {

    @Autowired
    private EntityConverter entityConverter;

    @AfterMapping
    protected void mapEntityDefFields(EntityTemplateDto source, @MappingTarget EntityTemplate entity) {
        entity.getValues().forEach(value -> value.setEntityTemplate(entity));
    }

    /**
     * Расширяет карту значений для сущности по идентификатору
     *
     * @param entityTemplate
     * @return
     */
    private Map<String, Object> fillFieldMap(
            @Nullable EntityTemplate entityTemplate,
            Map<String, Object> res
    ) {
        if (entityTemplate == null) {
            return res;
        }
        // служебные поля
        res.put("id", entityTemplate.getId().toString());
        if (entityTemplate.getCreatedTs() != null) {
            res.put("createdTs", entityTemplate.getCreatedTs().toOffsetDateTime());
        }
        if (entityTemplate.getUpdatedTs() != null) {
            res.put("updatedTs", entityTemplate.getUpdatedTs().toOffsetDateTime());
        }

        // поля ниже могут быть переопределены динамической частью шаблона, поэтому выносятся с перфиксом
        res.put("_description", entityTemplate.getDescription());
        res.put("_groupName", entityTemplate.getGroupName());
        res.put("_status", entityTemplate.getStatus());
        res.put("_name", entityTemplate.getName());

        entityTemplate.getValues().stream()
                .filter(v -> v.getFieldDef().isVisibleListView())
                .forEach(v ->
                        res.put(v.getFieldDef().getCode(), entityConverter.getValue(v, true))
                );
        return res;
    }

    /**
     * Строит карту значений для шаблона сущности
     *
     * @param entityTemplate
     * @return
     */
    public Map<String, Object> makeFieldMap(EntityTemplate entityTemplate) {
        return fillFieldMap(entityTemplate, new HashMap<>());
    }
}
