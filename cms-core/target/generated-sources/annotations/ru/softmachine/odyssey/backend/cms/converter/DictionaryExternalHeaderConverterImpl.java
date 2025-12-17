package ru.softmachine.odyssey.backend.cms.converter;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.DictionaryExternalHeaderDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.entity.DictionaryExternalHeader;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-17T17:26:02+0500",
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
