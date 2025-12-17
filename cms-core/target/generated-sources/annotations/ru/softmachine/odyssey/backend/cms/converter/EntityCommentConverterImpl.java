package ru.softmachine.odyssey.backend.cms.converter;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.EntityCommentDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.entity.Entity;
import ru.softmachine.odyssey.backend.cms.entity.EntityComment;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-17T17:26:02+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class EntityCommentConverterImpl extends EntityCommentConverter {

    @Autowired
    private ReferenceMapper referenceMapper;
    @Autowired
    private EntityConverter entityConverter;

    @Override
    public EntityCommentDto convertToDto(EntityComment entity) {
        if ( entity == null ) {
            return null;
        }

        EntityCommentDto entityCommentDto = new EntityCommentDto();

        entityCommentDto.setId( entity.getId() );
        entityCommentDto.setCreatedTs( entityConverter.deserializeDateTime( entity.getCreatedTs() ) );
        entityCommentDto.setUpdatedTs( entityConverter.deserializeDateTime( entity.getUpdatedTs() ) );
        entityCommentDto.setTitle( entity.getTitle() );
        entityCommentDto.setMessage( entity.getMessage() );
        entityCommentDto.setEntity( entityConverter.convertToBaseRef( entity.getEntity() ) );
        entityCommentDto.setAuthor( entity.getAuthor() );
        entityCommentDto.setAuthorEmail( entity.getAuthorEmail() );

        return entityCommentDto;
    }

    @Override
    public BaseRef convertToBaseRef(EntityComment entity) {
        if ( entity == null ) {
            return null;
        }

        BaseRef baseRef = new BaseRef();

        baseRef.setId( entity.getId() );

        return baseRef;
    }

    @Override
    public EntityComment convertToModel(EntityCommentDto dto) {
        if ( dto == null ) {
            return null;
        }

        EntityComment entityComment = referenceMapper.resolve( dto, EntityComment.class );

        entityComment.setId( dto.getId() );
        entityComment.setTitle( dto.getTitle() );
        entityComment.setMessage( dto.getMessage() );
        entityComment.setEntity( baseRefToEntity( dto.getEntity() ) );
        entityComment.setAuthor( dto.getAuthor() );
        entityComment.setAuthorEmail( dto.getAuthorEmail() );

        return entityComment;
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
