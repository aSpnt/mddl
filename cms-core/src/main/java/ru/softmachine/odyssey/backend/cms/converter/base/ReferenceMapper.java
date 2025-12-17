package ru.softmachine.odyssey.backend.cms.converter.base;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import ru.softmachine.odyssey.backend.cms.config.ConverterConfig;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseStringDto;
import ru.softmachine.odyssey.backend.cms.entity.base.BaseEntity;
import ru.softmachine.odyssey.backend.cms.exception.EntityNotFoundException;

import java.util.UUID;

@Mapper(config = ConverterConfig.class)
public class ReferenceMapper {

    @PersistenceContext
    private EntityManager entityManager;

    @SneakyThrows
    @ObjectFactory
    public <T extends BaseEntity> T resolve(BaseDto baseDto, @TargetType Class<T> type) {
        return (baseDto.getId() != null)
                ? entityManager.find(type, baseDto.getId())
                : type.getDeclaredConstructor().newInstance();
    }

    @SneakyThrows
    @ObjectFactory
    public <T extends BaseEntity> T resolve(BaseStringDto baseDto, @TargetType Class<T> type) {
        return (baseDto.getId() != null)
                ? entityManager.find(type, UUID.fromString(baseDto.getId()))
                : type.getDeclaredConstructor().newInstance();
    }

    @SneakyThrows
    @ObjectFactory
    public <T extends BaseEntity> T resolve(BaseRef baseRef, @TargetType Class<T> type) {
        if (baseRef.getId() != null) {
            var value = entityManager.find(type, baseRef.getId());
            if (value != null) {
                return (T) value;
            }
            throw new EntityNotFoundException("Entity Def was not found",
                    baseRef.getId().toString());
        }
        throw new IllegalArgumentException();
    }
}
