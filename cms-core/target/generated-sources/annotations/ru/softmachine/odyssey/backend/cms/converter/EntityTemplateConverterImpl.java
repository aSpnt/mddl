package ru.softmachine.odyssey.backend.cms.converter;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.EntityTemplateDto;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.entity.EntityTemplate;
import ru.softmachine.odyssey.backend.cms.entity.FieldValue;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-17T17:26:12+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class EntityTemplateConverterImpl extends EntityTemplateConverter {

    @Autowired
    private ReferenceMapper referenceMapper;
    @Autowired
    private FieldValueConverter fieldValueConverter;

    @Override
    public EntityTemplateDto convertToDto(EntityTemplate entity) {
        if ( entity == null ) {
            return null;
        }

        EntityTemplateDto entityTemplateDto = new EntityTemplateDto();

        entityTemplateDto.setId( entity.getId() );
        entityTemplateDto.setCreatedTs( entity.getCreatedTs() );
        entityTemplateDto.setUpdatedTs( entity.getUpdatedTs() );
        entityTemplateDto.setName( entity.getName() );
        entityTemplateDto.setCode( entity.getCode() );
        entityTemplateDto.setSeq( entity.getSeq() );
        entityTemplateDto.setDescription( entity.getDescription() );
        entityTemplateDto.setGroupName( entity.getGroupName() );
        entityTemplateDto.setStatus( entity.getStatus() );
        entityTemplateDto.setEntityDef( entityDefToBaseRef( entity.getEntityDef() ) );
        entityTemplateDto.setValues( fieldValueListToFieldValueDtoList( entity.getValues() ) );

        return entityTemplateDto;
    }

    @Override
    public BaseRef convertToBaseRef(EntityTemplate entity) {
        if ( entity == null ) {
            return null;
        }

        BaseRef baseRef = new BaseRef();

        baseRef.setId( entity.getId() );
        baseRef.setName( entity.getName() );

        return baseRef;
    }

    @Override
    public EntityTemplate convertToModel(EntityTemplateDto dto) {
        if ( dto == null ) {
            return null;
        }

        EntityTemplate entityTemplate = referenceMapper.resolve( dto, EntityTemplate.class );

        entityTemplate.setId( dto.getId() );
        entityTemplate.setName( dto.getName() );
        entityTemplate.setCode( dto.getCode() );
        entityTemplate.setSeq( dto.getSeq() );
        entityTemplate.setDescription( dto.getDescription() );
        entityTemplate.setGroupName( dto.getGroupName() );
        entityTemplate.setStatus( dto.getStatus() );
        entityTemplate.setEntityDef( baseRefToEntityDef( dto.getEntityDef() ) );
        entityTemplate.setValues( fieldValueDtoListToFieldValueList( dto.getValues() ) );

        mapEntityDefFields( dto, entityTemplate );

        return entityTemplate;
    }

    protected BaseRef entityDefToBaseRef(EntityDef entityDef) {
        if ( entityDef == null ) {
            return null;
        }

        BaseRef baseRef = new BaseRef();

        baseRef.setId( entityDef.getId() );
        baseRef.setName( entityDef.getName() );

        return baseRef;
    }

    protected List<FieldValueDto> fieldValueListToFieldValueDtoList(List<FieldValue> list) {
        if ( list == null ) {
            return null;
        }

        List<FieldValueDto> list1 = new ArrayList<FieldValueDto>( list.size() );
        for ( FieldValue fieldValue : list ) {
            list1.add( fieldValueConverter.convertToDto( fieldValue ) );
        }

        return list1;
    }

    protected EntityDef baseRefToEntityDef(BaseRef baseRef) {
        if ( baseRef == null ) {
            return null;
        }

        EntityDef entityDef = referenceMapper.resolve( baseRef, EntityDef.class );

        entityDef.setId( baseRef.getId() );
        entityDef.setName( baseRef.getName() );

        return entityDef;
    }

    protected List<FieldValue> fieldValueDtoListToFieldValueList(List<FieldValueDto> list) {
        if ( list == null ) {
            return null;
        }

        List<FieldValue> list1 = new ArrayList<FieldValue>( list.size() );
        for ( FieldValueDto fieldValueDto : list ) {
            list1.add( fieldValueConverter.convertToModel( fieldValueDto ) );
        }

        return list1;
    }
}
