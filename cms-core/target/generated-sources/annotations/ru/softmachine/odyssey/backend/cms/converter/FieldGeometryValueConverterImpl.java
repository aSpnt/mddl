package ru.softmachine.odyssey.backend.cms.converter;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.FieldGeometryValueDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.entity.FieldGeometryValue;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-17T17:26:02+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class FieldGeometryValueConverterImpl extends FieldGeometryValueConverter {

    @Autowired
    private ReferenceMapper referenceMapper;
    @Autowired
    private GeometryConverter geometryConverter;

    @Override
    public FieldGeometryValueDto convertToDto(FieldGeometryValue entity) {
        if ( entity == null ) {
            return null;
        }

        FieldGeometryValueDto fieldGeometryValueDto = new FieldGeometryValueDto();

        fieldGeometryValueDto.setId( entity.getId() );
        fieldGeometryValueDto.setCreatedTs( entity.getCreatedTs() );
        fieldGeometryValueDto.setUpdatedTs( entity.getUpdatedTs() );
        fieldGeometryValueDto.setTitle( entity.getTitle() );
        fieldGeometryValueDto.setMessage( entity.getMessage() );
        fieldGeometryValueDto.setGeom( geometryConverter.mapToGeoPoint( entity.getGeom() ) );

        return fieldGeometryValueDto;
    }

    @Override
    public BaseRef convertToBaseRef(FieldGeometryValue entity) {
        if ( entity == null ) {
            return null;
        }

        BaseRef baseRef = new BaseRef();

        baseRef.setId( entity.getId() );

        return baseRef;
    }

    @Override
    public FieldGeometryValue convertToModel(FieldGeometryValueDto dto) {
        if ( dto == null ) {
            return null;
        }

        FieldGeometryValue fieldGeometryValue = referenceMapper.resolve( dto, FieldGeometryValue.class );

        fieldGeometryValue.setId( dto.getId() );
        fieldGeometryValue.setTitle( dto.getTitle() );
        fieldGeometryValue.setMessage( dto.getMessage() );
        fieldGeometryValue.setGeom( geometryConverter.mapToPoint( dto.getGeom() ) );

        return fieldGeometryValue;
    }

    @Override
    public FieldGeometryValueDto mapWithoutId(FieldGeometryValue dto) {
        if ( dto == null ) {
            return null;
        }

        FieldGeometryValueDto fieldGeometryValueDto = new FieldGeometryValueDto();

        fieldGeometryValueDto.setTitle( dto.getTitle() );
        fieldGeometryValueDto.setMessage( dto.getMessage() );
        fieldGeometryValueDto.setGeom( geometryConverter.mapToGeoPoint( dto.getGeom() ) );

        return fieldGeometryValueDto;
    }
}
