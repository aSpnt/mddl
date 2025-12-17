package ru.softmachine.odyssey.backend.cms.converter;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.FieldValidationDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.entity.validation.FieldValidation;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-17T17:26:02+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class FieldValidationConverterImpl extends FieldValidationConverter {

    @Autowired
    private ReferenceMapper referenceMapper;

    @Override
    public FieldValidationDto convertToDto(FieldValidation entity) {
        if ( entity == null ) {
            return null;
        }

        FieldValidationDto fieldValidationDto = new FieldValidationDto();

        fieldValidationDto.setId( entity.getId() );
        fieldValidationDto.setCreatedTs( entity.getCreatedTs() );
        fieldValidationDto.setUpdatedTs( entity.getUpdatedTs() );
        fieldValidationDto.setType( entity.getType() );
        fieldValidationDto.setTextValue( entity.getTextValue() );
        fieldValidationDto.setDoubleValue( entity.getDoubleValue() );
        fieldValidationDto.setIntValue( entity.getIntValue() );
        List<String> list = entity.getTextArrayValue();
        if ( list != null ) {
            fieldValidationDto.setTextArrayValue( new ArrayList<String>( list ) );
        }
        fieldValidationDto.setMessage( entity.getMessage() );

        return fieldValidationDto;
    }

    @Override
    public BaseRef convertToBaseRef(FieldValidation entity) {
        if ( entity == null ) {
            return null;
        }

        BaseRef baseRef = new BaseRef();

        baseRef.setId( entity.getId() );

        return baseRef;
    }

    @Override
    public FieldValidation convertToModel(FieldValidationDto dto) {
        if ( dto == null ) {
            return null;
        }

        FieldValidation fieldValidation = referenceMapper.resolve( dto, FieldValidation.class );

        fieldValidation.setId( dto.getId() );
        fieldValidation.setType( dto.getType() );
        fieldValidation.setTextValue( dto.getTextValue() );
        fieldValidation.setDoubleValue( dto.getDoubleValue() );
        fieldValidation.setIntValue( dto.getIntValue() );
        List<String> list = dto.getTextArrayValue();
        if ( list != null ) {
            fieldValidation.setTextArrayValue( new ArrayList<String>( list ) );
        }
        fieldValidation.setMessage( dto.getMessage() );

        return fieldValidation;
    }
}
