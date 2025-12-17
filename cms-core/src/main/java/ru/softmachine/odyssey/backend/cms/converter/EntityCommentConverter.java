package ru.softmachine.odyssey.backend.cms.converter;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import ru.softmachine.odyssey.backend.cms.config.ConverterConfig;
import ru.softmachine.odyssey.backend.cms.converter.base.BaseConverter;
import ru.softmachine.odyssey.backend.cms.converter.base.ReferenceMapper;
import ru.softmachine.odyssey.backend.cms.dto.EntityCommentDto;
import ru.softmachine.odyssey.backend.cms.entity.EntityComment;

@Mapper(config = ConverterConfig.class,
        uses = {
                ReferenceMapper.class,
                EntityConverter.class,
        }
)
@Slf4j
public abstract class EntityCommentConverter implements BaseConverter<EntityComment, EntityCommentDto> {
}
