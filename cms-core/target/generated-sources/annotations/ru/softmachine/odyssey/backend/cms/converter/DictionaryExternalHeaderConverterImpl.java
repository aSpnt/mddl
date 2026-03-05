package com.aspnt.mddl.converter;

import javax.annotation.processing.Generated;

import com.aspnt.mddl.converter.DictionaryExternalHeaderConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.aspnt.mddl.converter.base.ReferenceMapper;
import com.aspnt.mddl.dto.DictionaryExternalHeaderDto;
import com.aspnt.mddl.dto.base.BaseRef;
import com.aspnt.mddl.entity.DictionaryExternalHeader;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-04T20:17:50+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class DictionaryExternalHeaderConverterImpl extends DictionaryExternalHeaderConverter {

    @Autowired
    private ReferenceMapper referenceMapper;

    @Override
    public DictionaryExternalHeaderDto convertToDto(DictionaryExternalHeader entity) {
        if ( entity == null ) {
            return null;
        }

        DictionaryExternalHeaderDto dictionaryExternalHeaderDto = new DictionaryExternalHeaderDto();

        dictionaryExternalHeaderDto.setId( entity.getId() );
        dictionaryExternalHeaderDto.setCreatedTs( entity.getCreatedTs() );
        dictionaryExternalHeaderDto.setUpdatedTs( entity.getUpdatedTs() );
        dictionaryExternalHeaderDto.setName( entity.getName() );

        return dictionaryExternalHeaderDto;
    }

    @Override
    public BaseRef convertToBaseRef(DictionaryExternalHeader entity) {
        if ( entity == null ) {
            return null;
        }

        BaseRef baseRef = new BaseRef();

        baseRef.setId( entity.getId() );
        baseRef.setName( entity.getName() );

        return baseRef;
    }

    @Override
    public DictionaryExternalHeader convertToModel(DictionaryExternalHeaderDto dto) {
        if ( dto == null ) {
            return null;
        }

        DictionaryExternalHeader dictionaryExternalHeader = referenceMapper.resolve( dto, DictionaryExternalHeader.class );

        dictionaryExternalHeader.setId( dto.getId() );
        dictionaryExternalHeader.setName( dto.getName() );

        return dictionaryExternalHeader;
    }
}
