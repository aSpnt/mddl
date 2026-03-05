package com.aspnt.mddl.converter;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import com.aspnt.mddl.config.ConverterConfig;
import com.aspnt.mddl.converter.base.BaseConverter;
import com.aspnt.mddl.converter.base.ReferenceMapper;
import com.aspnt.mddl.dto.EntityCommentDto;
import com.aspnt.mddl.entity.EntityComment;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
                EntityConverter.class,
        }
)
@Slf4j
public abstract class EntityCommentConverter implements BaseConverter<EntityComment, EntityCommentDto> {
}
