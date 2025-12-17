package ru.softmachine.odyssey.backend.cms.converter;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;
import ru.softmachine.odyssey.backend.cms.entity.Entity;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-17T17:26:12+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class EntityUtilsConverterImpl extends EntityUtilsConverter {

    @Override
    public EntityDto mapWithoutIdAndValues(Entity dto) {
        if ( dto == null ) {
            return null;
        }

        EntityDto entityDto = new EntityDto();

        entityDto.setVersion( dto.getVersion() );
        if ( dto.getSeq() != null ) {
            entityDto.setSeq( dto.getSeq() );
        }
        entityDto.setLastStatusChangeTs( dto.getLastStatusChangeTs() );
        entityDto.setSlug( dto.getSlug() );
        entityDto.setSlugLock( dto.isSlugLock() );
        entityDto.setDeleteLock( dto.isDeleteLock() );
        entityDto.setActive( dto.isActive() );
        entityDto.setEntityDef( entityDefToBaseRef( dto.getEntityDef() ) );
        entityDto.setAuthor( dto.getAuthor() );
        entityDto.setAuthorEmail( dto.getAuthorEmail() );
        entityDto.setEntityTemplateName( dto.getEntityTemplateName() );

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
}
