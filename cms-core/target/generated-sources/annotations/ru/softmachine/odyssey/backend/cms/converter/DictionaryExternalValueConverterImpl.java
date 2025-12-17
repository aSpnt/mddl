package ru.softmachine.odyssey.backend.cms.converter;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseExternalRef;
import ru.softmachine.odyssey.backend.cms.entity.ExternalDictionaryValue;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-17T17:26:02+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class DictionaryExternalValueConverterImpl extends DictionaryExternalValueConverter {

    @Override
    public BaseExternalRef convertToDto(ExternalDictionaryValue entity) {
        if ( entity == null ) {
            return null;
        }

        BaseExternalRef baseExternalRef = new BaseExternalRef();

        baseExternalRef.setId( entity.getId() );
        baseExternalRef.setName( entity.getName() );
        if ( entity.getSeq() != null ) {
            baseExternalRef.setSeq( entity.getSeq() );
        }
        baseExternalRef.setDescription( entity.getDescription() );
        baseExternalRef.setImg( entity.getImg() );
        baseExternalRef.setRef( entity.getRef() );

        return baseExternalRef;
    }

    @Override
    public ExternalDictionaryValue convertToModel(BaseExternalRef dto) {
        if ( dto == null ) {
            return null;
        }

        ExternalDictionaryValue externalDictionaryValue = new ExternalDictionaryValue();

        externalDictionaryValue.setId( dto.getId() );
        externalDictionaryValue.setSeq( dto.getSeq() );
        externalDictionaryValue.setName( dto.getName() );
        externalDictionaryValue.setDescription( dto.getDescription() );
        externalDictionaryValue.setImg( dto.getImg() );
        externalDictionaryValue.setRef( dto.getRef() );

        return externalDictionaryValue;
    }

    @Override
    public BaseExternalRef mapWithoutId(ExternalDictionaryValue dto) {
        if ( dto == null ) {
            return null;
        }

        BaseExternalRef baseExternalRef = new BaseExternalRef();

        baseExternalRef.setName( dto.getName() );
        if ( dto.getSeq() != null ) {
            baseExternalRef.setSeq( dto.getSeq() );
        }
        baseExternalRef.setDescription( dto.getDescription() );
        baseExternalRef.setImg( dto.getImg() );
        baseExternalRef.setRef( dto.getRef() );

        return baseExternalRef;
    }
}
