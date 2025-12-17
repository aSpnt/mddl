package ru.softmachine.odyssey.backend.cms.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.softmachine.odyssey.backend.cms.converter.EntityConverter;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;
import ru.softmachine.odyssey.backend.cms.entity.Entity;
import ru.softmachine.odyssey.backend.cms.entity.Entity_;
import ru.softmachine.odyssey.backend.cms.entity.FieldValue_;
import ru.softmachine.odyssey.backend.cms.entity.entitydef.EntityDef_;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef_;
import ru.softmachine.odyssey.backend.cms.repository.EntityRepository;

import java.util.UUID;
import java.util.function.Function;

/**
 * Сервис создан для выделения части функционала справочников из общего
 * сервиса для работы с Entity
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DictionaryService {

    private final EntityRepository entityRepository;
    private final EntityConverter entityConverter;

    /**
     * Создание элемента справочника
     *
     * @param value карта значений справочника по которым он будет создан
     * @param converter конвертер для результата создания
     * @return
     * @param <T>
     */
    public <T> T createDictionaryElement(
            EntityDto value,
            Function<Entity, T> converter
    ) {
        return converter.apply(entityRepository.save(
                entityConverter.convertToModel(value)));
    }

    /**
     * Поиск Entity справочника по динамическому полю и его значению
     * (ключу справочника который хранится в динамическом поле)
     *
     * @param entityDefId
     * @param fieldDefId
     * @param enumKey
     * @param converter
     * @return
     * @param <T>
     */
    public <T> T findDictionaryValueBySerializedEnum(
            UUID entityDefId,
            UUID fieldDefId,
            String enumKey,
            Function<Entity, T> converter
    ) {
        return entityRepository.findAll(
                        makeSpecificationForOneValue(entityDefId, fieldDefId, enumKey)
                ).stream()
                .map(converter)
                .findFirst()
                .orElse(null);
    }

    /**
     * Поиск Entity справочника по фиксированному полу (обычно id)
     *
     * @param entityDefId
     * @param fieldDefCode
     * @param key
     * @param converter
     * @return
     * @param <T>
     */
    public <T> T findDictionaryValueByFixedField(
            UUID entityDefId,
            String fieldDefCode,
            Object key,
            Function<Entity, T> converter) {
        return entityRepository.findAll(
                        makeSpecificationForOneFixedValue(entityDefId, fieldDefCode, key)
                ).stream()
                .map(converter)
                .findFirst()
                .orElse(null);
    }

    private Specification<Entity> makeSpecificationForOneFixedValue(UUID entityDefId, String fieldDefCode, Object value) {
        return (root, query, builderSource) -> {
            var builder = (HibernateCriteriaBuilder) builderSource;

            var predicate = builder.equal(root.get(Entity_.entityDef).get(EntityDef_.id), entityDefId);

            return builder.and(predicate,
                    builder.equal(root.get(fieldDefCode), value));
        };
    }

    private Specification<Entity> makeSpecificationForOneValue(UUID entityDefId, UUID fieldDefId, String value) {
        return (root, query, builderSource) -> {
            var builder = (HibernateCriteriaBuilder) builderSource;

            var predicate = builder.equal(root.get(Entity_.entityDef).get(EntityDef_.id), entityDefId);

            var fieldValueJoin = root.join(Entity_.values);
            var fieldDefJoin = fieldValueJoin.join(FieldValue_.fieldDef);

            return builder.and(predicate,
                    builder.equal(fieldDefJoin.get(FieldDef_.id), fieldDefId),
                    builder.equal(fieldValueJoin.get(FieldValue_.textValue), value));
        };
    }
}
