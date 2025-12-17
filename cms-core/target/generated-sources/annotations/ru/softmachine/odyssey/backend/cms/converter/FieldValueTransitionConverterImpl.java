package ru.softmachine.odyssey.backend.cms.converter;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.FieldValueTransitionDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.entity.Entity;
import ru.softmachine.odyssey.backend.cms.entity.FieldValueTransition;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-17T17:26:02+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class FieldValueTransitionConverterImpl extends FieldValueTransitionConverter {

    @Autowired
    private ReferenceMapper referenceMapper;
    @Autowired
    private EntityConverter entityConverter;

    @Override
    public FieldValueTransitionDto convertToDto(FieldValueTransition entity) {
        if ( entity == null ) {
            return null;
        }

        FieldValueTransitionDto fieldValueTransitionDto = new FieldValueTransitionDto();

        fieldValueTransitionDto.setId( entity.getId() );
        fieldValueTransitionDto.setCreatedTs( entityConverter.deserializeDateTime( entity.getCreatedTs() ) );
        fieldValueTransitionDto.setUpdatedTs( entityConverter.deserializeDateTime( entity.getUpdatedTs() ) );
        fieldValueTransitionDto.setEntityFrom( entityConverter.convertToBaseRef( entity.getEntityFrom() ) );
        fieldValueTransitionDto.setEntityTo( entityConverter.convertToBaseRef( entity.getEntityTo() ) );
        fieldValueTransitionDto.setShowButton( entity.isShowButton() );
        fieldValueTransitionDto.setUpdateLastStatusTs( entity.isUpdateLastStatusTs() );
        fieldValueTransitionDto.setEntityActiveStatus( entity.isEntityActiveStatus() );
        fieldValueTransitionDto.setMessage( entity.getMessage() );
        fieldValueTransitionDto.setSeq( entity.getSeq() );
        fieldValueTransitionDto.setNeedComment( entity.isNeedComment() );
        fieldValueTransitionDto.setCommentRequired( entity.isCommentRequired() );
        fieldValueTransitionDto.setCommentTitle( entity.getCommentTitle() );
        fieldValueTransitionDto.setCommentNote( entity.getCommentNote() );
        List<String> list = entity.getFieldCodes();
        if ( list != null ) {
            fieldValueTransitionDto.setFieldCodes( new ArrayList<String>( list ) );
        }

        return fieldValueTransitionDto;
    }

    @Override
    public BaseRef convertToBaseRef(FieldValueTransition entity) {
        if ( entity == null ) {
            return null;
        }

        BaseRef baseRef = new BaseRef();

        baseRef.setId( entity.getId() );

        return baseRef;
    }

    @Override
    public FieldValueTransition convertToModel(FieldValueTransitionDto dto) {
        if ( dto == null ) {
            return null;
        }

        FieldValueTransition fieldValueTransition = referenceMapper.resolve( dto, FieldValueTransition.class );

        fieldValueTransition.setId( dto.getId() );
        fieldValueTransition.setEntityFrom( baseRefToEntity( dto.getEntityFrom() ) );
        fieldValueTransition.setEntityTo( baseRefToEntity( dto.getEntityTo() ) );
        if ( dto.getShowButton() != null ) {
            fieldValueTransition.setShowButton( dto.getShowButton() );
        }
        if ( dto.getUpdateLastStatusTs() != null ) {
            fieldValueTransition.setUpdateLastStatusTs( dto.getUpdateLastStatusTs() );
        }
        if ( dto.getEntityActiveStatus() != null ) {
            fieldValueTransition.setEntityActiveStatus( dto.getEntityActiveStatus() );
        }
        fieldValueTransition.setCommentTitle( dto.getCommentTitle() );
        fieldValueTransition.setMessage( dto.getMessage() );
        fieldValueTransition.setSeq( dto.getSeq() );
        if ( dto.getNeedComment() != null ) {
            fieldValueTransition.setNeedComment( dto.getNeedComment() );
        }
        if ( dto.getCommentRequired() != null ) {
            fieldValueTransition.setCommentRequired( dto.getCommentRequired() );
        }
        fieldValueTransition.setCommentNote( dto.getCommentNote() );
        List<String> list = dto.getFieldCodes();
        if ( list != null ) {
            fieldValueTransition.setFieldCodes( new ArrayList<String>( list ) );
        }

        return fieldValueTransition;
    }

    protected Entity baseRefToEntity(BaseRef baseRef) {
        if ( baseRef == null ) {
            return null;
        }

        Entity entity = referenceMapper.resolve( baseRef, Entity.class );

        entity.setId( baseRef.getId() );

        return entity;
    }
}
