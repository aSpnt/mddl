package ru.softmachine.odyssey.backend.cms.converter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.DictionaryExternalDto;
import ru.softmachine.odyssey.backend.cms.dto.DictionaryExternalHeaderDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal;
import ru.softmachine.odyssey.backend.cms.entity.DictionaryExternalHeader;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-17T17:26:02+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class DictionaryExternalConverterImpl extends DictionaryExternalConverter {

    @Autowired
    private ReferenceMapper referenceMapper;
    @Autowired
    private DictionaryExternalHeaderConverter dictionaryExternalHeaderConverter;

    @Override
    public DictionaryExternalDto convertToDto(DictionaryExternal entity) {
        if ( entity == null ) {
            return null;
        }

        DictionaryExternalDto dictionaryExternalDto = new DictionaryExternalDto();

        dictionaryExternalDto.setId( entity.getId() );
        dictionaryExternalDto.setCreatedTs( entity.getCreatedTs() );
        dictionaryExternalDto.setUpdatedTs( entity.getUpdatedTs() );
        dictionaryExternalDto.setName( entity.getName() );
        dictionaryExternalDto.setCode( entity.getCode() );
        dictionaryExternalDto.setUrl( entity.getUrl() );
        List<String> list = entity.getParam();
        if ( list != null ) {
            dictionaryExternalDto.setParam( new ArrayList<String>( list ) );
        }
        dictionaryExternalDto.setMethod( entity.getMethod() );
        dictionaryExternalDto.setResponseParam( entity.getResponseParam() );
        Map<String, Object> map = entity.getDefaultBody();
        if ( map != null ) {
            dictionaryExternalDto.setDefaultBody( new LinkedHashMap<String, Object>( map ) );
        }
        dictionaryExternalDto.setHeaders( dictionaryExternalHeaderListToDictionaryExternalHeaderDtoList( entity.getHeaders() ) );
        dictionaryExternalDto.setEntityDef( entityDefToBaseRef( entity.getEntityDef() ) );

        return dictionaryExternalDto;
    }

    @Override
    public BaseRef convertToBaseRef(DictionaryExternal entity) {
        if ( entity == null ) {
            return null;
        }

        BaseRef baseRef = new BaseRef();

        baseRef.setId( entity.getId() );
        baseRef.setName( entity.getName() );

        return baseRef;
    }

    @Override
    public DictionaryExternal convertToModel(DictionaryExternalDto dto) {
        if ( dto == null ) {
            return null;
        }

        DictionaryExternal dictionaryExternal = referenceMapper.resolve( dto, DictionaryExternal.class );

        dictionaryExternal.setId( dto.getId() );
        dictionaryExternal.setHeaders( dictionaryExternalHeaderDtoListToDictionaryExternalHeaderList( dto.getHeaders() ) );
        dictionaryExternal.setName( dto.getName() );
        dictionaryExternal.setCode( dto.getCode() );
        dictionaryExternal.setUrl( dto.getUrl() );
        dictionaryExternal.setMethod( dto.getMethod() );
        List<String> list1 = dto.getParam();
        if ( list1 != null ) {
            dictionaryExternal.setParam( new ArrayList<String>( list1 ) );
        }
        dictionaryExternal.setResponseParam( dto.getResponseParam() );
        Map<String, Object> map = dto.getDefaultBody();
        if ( map != null ) {
            dictionaryExternal.setDefaultBody( new LinkedHashMap<String, Object>( map ) );
        }
        dictionaryExternal.setEntityDef( baseRefToEntityDef( dto.getEntityDef() ) );

        return dictionaryExternal;
    }

    protected List<DictionaryExternalHeaderDto> dictionaryExternalHeaderListToDictionaryExternalHeaderDtoList(List<DictionaryExternalHeader> list) {
        if ( list == null ) {
            return null;
        }

        List<DictionaryExternalHeaderDto> list1 = new ArrayList<DictionaryExternalHeaderDto>( list.size() );
        for ( DictionaryExternalHeader dictionaryExternalHeader : list ) {
            list1.add( dictionaryExternalHeaderConverter.convertToDto( dictionaryExternalHeader ) );
        }

        return list1;
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

    protected List<DictionaryExternalHeader> dictionaryExternalHeaderDtoListToDictionaryExternalHeaderList(List<DictionaryExternalHeaderDto> list) {
        if ( list == null ) {
            return null;
        }

        List<DictionaryExternalHeader> list1 = new ArrayList<DictionaryExternalHeader>( list.size() );
        for ( DictionaryExternalHeaderDto dictionaryExternalHeaderDto : list ) {
            list1.add( dictionaryExternalHeaderConverter.convertToModel( dictionaryExternalHeaderDto ) );
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
}
