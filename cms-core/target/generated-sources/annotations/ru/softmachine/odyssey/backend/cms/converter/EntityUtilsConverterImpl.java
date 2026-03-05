package com.aspnt.mddl.converter;

import javax.annotation.processing.Generated;

import com.aspnt.mddl.converter.EntityUtilsConverter;
import org.springframework.stereotype.Component;
import com.aspnt.mddl.dto.base.BaseRef;
import com.aspnt.mddl.dto.entity.EntityDto;
import com.aspnt.mddl.entity.Entity;
import com.aspnt.mddl.entity.entitydef.EntityDef;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-04T20:17:50+0500",
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
