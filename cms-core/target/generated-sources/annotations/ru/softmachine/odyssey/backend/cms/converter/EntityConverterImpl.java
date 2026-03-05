package com.aspnt.mddl.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;

import com.aspnt.mddl.converter.CommonBaseRefConverter;
import com.aspnt.mddl.converter.EntityConverter;
import com.aspnt.mddl.converter.FieldValueConverter;
import com.aspnt.mddl.converter.FieldValueUtilsConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.aspnt.mddl.converter.base.ReferenceMapper;
import com.aspnt.mddl.converter.context.EntityMappingContext;
import com.aspnt.mddl.dto.FieldValueDto;
import com.aspnt.mddl.dto.base.BaseRef;
import com.aspnt.mddl.dto.base.BaseSlugRef;
import com.aspnt.mddl.dto.base.BaseStringDto;
import com.aspnt.mddl.dto.entity.EntityDto;
import com.aspnt.mddl.entity.Entity;
import com.aspnt.mddl.entity.FieldValue;
import com.aspnt.mddl.entity.entitydef.EntityDef;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-04T20:17:50+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class EntityConverterImpl extends EntityConverter {

    @Autowired
    private ReferenceMapper referenceMapper;
    @Autowired
    private FieldValueConverter fieldValueConverter;
    @Autowired
    private FieldValueUtilsConverter fieldValueUtilsConverter;
    @Autowired
    private CommonBaseRefConverter commonBaseRefConverter;

    @Override
    public EntityDto convertToDto(Entity entity) {
        if ( entity == null ) {
            return null;
        }

        EntityDto entityDto = new EntityDto();

        if ( entity.getId() != null ) {
            entityDto.setId( entity.getId().toString() );
        }
        entityDto.setCreatedTs( deserializeDateTime( entity.getCreatedTs() ) );
        entityDto.setUpdatedTs( deserializeDateTime( entity.getUpdatedTs() ) );
        entityDto.setVersion( entity.getVersion() );
        if ( entity.getSeq() != null ) {
            entityDto.setSeq( entity.getSeq() );
        }
        entityDto.setLastStatusChangeTs( deserializeDateTime( entity.getLastStatusChangeTs() ) );
        entityDto.setSlug( entity.getSlug() );
        entityDto.setSlugLock( entity.isSlugLock() );
        entityDto.setDeleteLock( entity.isDeleteLock() );
        entityDto.setActive( entity.isActive() );
        entityDto.setEntityDef( entityDefToBaseRef( entity.getEntityDef() ) );
        entityDto.setValues( fieldValueListToFieldValueDtoList( entity.getValues() ) );
        entityDto.setAuthor( entity.getAuthor() );
        entityDto.setAuthorEmail( entity.getAuthorEmail() );
        entityDto.setEntityTemplateName( entity.getEntityTemplateName() );

        return entityDto;
    }

    @Override
    public BaseRef convertToBaseRef(Entity entity) {
        if ( entity == null ) {
            return null;
        }

        BaseRef baseRef = new BaseRef();

        baseRef.setId( entity.getId() );

        mapEntityToBaseRef( entity, baseRef );

        return baseRef;
    }

    @Override
    public Entity convertToModel(EntityDto dto) {
        if ( dto == null ) {
            return null;
        }

        Entity entity = referenceMapper.resolve( dto, Entity.class );

        if ( dto.getId() != null ) {
            entity.setId( UUID.fromString( dto.getId() ) );
        }
        entity.setCreatedTs( deserializeDateTime( dto.getCreatedTs() ) );
        entity.setUpdatedTs( deserializeDateTime( dto.getUpdatedTs() ) );
        entity.setSlug( dto.getSlug() );
        entity.setVersion( dto.getVersion() );
        entity.setSeq( dto.getSeq() );
        entity.setLastStatusChangeTs( deserializeDateTime( dto.getLastStatusChangeTs() ) );
        if ( dto.getSlugLock() != null ) {
            entity.setSlugLock( dto.getSlugLock() );
        }
        if ( dto.getDeleteLock() != null ) {
            entity.setDeleteLock( dto.getDeleteLock() );
        }
        if ( dto.getActive() != null ) {
            entity.setActive( dto.getActive() );
        }
        entity.setEntityDef( commonBaseRefConverter.baseRefToEntityDef( dto.getEntityDef() ) );
        entity.setValues( fieldValueDtoListToFieldValueList( dto.getValues() ) );
        entity.setEntityTemplateName( dto.getEntityTemplateName() );

        mapEntityDefFields( dto, entity );
        checkVersion( dto, entity );

        return entity;
    }

    @Override
    public EntityDto convertToDtoWithContext(Entity entity, EntityMappingContext context) {
        if ( entity == null ) {
            return null;
        }

        EntityDto entityDto = new EntityDto();

        if ( entity.getId() != null ) {
            entityDto.setId( entity.getId().toString() );
        }
        entityDto.setCreatedTs( deserializeDateTime( entity.getCreatedTs() ) );
        entityDto.setUpdatedTs( deserializeDateTime( entity.getUpdatedTs() ) );
        entityDto.setVersion( entity.getVersion() );
        if ( entity.getSeq() != null ) {
            entityDto.setSeq( entity.getSeq() );
        }
        entityDto.setLastStatusChangeTs( deserializeDateTime( entity.getLastStatusChangeTs() ) );
        entityDto.setSlug( entity.getSlug() );
        entityDto.setSlugLock( entity.isSlugLock() );
        entityDto.setDeleteLock( entity.isDeleteLock() );
        entityDto.setActive( entity.isActive() );
        entityDto.setEntityDef( entityDefToBaseRef1( entity.getEntityDef(), context ) );
        entityDto.setValues( fieldValueListToFieldValueDtoList1( entity.getValues(), context ) );
        entityDto.setAuthor( entity.getAuthor() );
        entityDto.setAuthorEmail( entity.getAuthorEmail() );
        entityDto.setEntityTemplateName( entity.getEntityTemplateName() );

        evaluateExpressions( entity, entityDto, context );

        return entityDto;
    }

    @Override
    public BaseSlugRef convertToBaseSlugEntity(Entity entity) {
        if ( entity == null ) {
            return null;
        }

        BaseSlugRef baseSlugRef = new BaseSlugRef();

        if ( entity.getId() != null ) {
            baseSlugRef.setId( entity.getId().toString() );
        }
        baseSlugRef.setSlug( entity.getSlug() );

        return baseSlugRef;
    }

    @Override
    public Entity baseEntityMapperByRef(BaseStringDto ref) {
        if ( ref == null ) {
            return null;
        }

        Entity entity = referenceMapper.resolve( ref, Entity.class );

        return entity;
    }

    @Override
    public EntityDto mapWithoutId(Entity entity) {
        if ( entity == null ) {
            return null;
        }

        EntityDto entityDto = new EntityDto();

        entityDto.setValues( fieldValueListToFieldValueDtoList1( entity.getValues() ) );
        entityDto.setVersion( entity.getVersion() );
        if ( entity.getSeq() != null ) {
            entityDto.setSeq( entity.getSeq() );
        }
        entityDto.setLastStatusChangeTs( deserializeDateTime( entity.getLastStatusChangeTs() ) );
        entityDto.setSlug( entity.getSlug() );
        entityDto.setSlugLock( entity.isSlugLock() );
        entityDto.setDeleteLock( entity.isDeleteLock() );
        entityDto.setActive( entity.isActive() );
        entityDto.setEntityDef( entityDefToBaseRef( entity.getEntityDef() ) );
        entityDto.setAuthor( entity.getAuthor() );
        entityDto.setAuthorEmail( entity.getAuthorEmail() );
        entityDto.setEntityTemplateName( entity.getEntityTemplateName() );

        return entityDto;
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

    protected BaseRef entityDefToBaseRef1(EntityDef entityDef, EntityMappingContext context) {
        if ( entityDef == null ) {
            return null;
        }

        BaseRef baseRef = new BaseRef();

        baseRef.setId( entityDef.getId() );
        baseRef.setName( entityDef.getName() );

        return baseRef;
    }

    protected List<FieldValueDto> fieldValueListToFieldValueDtoList1(List<FieldValue> list) {
        if ( list == null ) {
            return null;
        }

        List<FieldValueDto> list1 = new ArrayList<FieldValueDto>( list.size() );
        for ( FieldValue fieldValue : list ) {
            list1.add( fieldValueUtilsConverter.mapWithoutId( fieldValue ) );
        }

        return list1;
    }
}
