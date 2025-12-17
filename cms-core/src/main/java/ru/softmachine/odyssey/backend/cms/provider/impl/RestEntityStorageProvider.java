package ru.softmachine.odyssey.backend.cms.provider.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import ru.softmachine.odyssey.backend.cms.converter.EntityConverter;
import ru.softmachine.odyssey.backend.cms.dto.EntitySeqDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseRef;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseStringRef;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityDto;
import ru.softmachine.odyssey.backend.cms.dto.entity.EntityPatchDto;
import ru.softmachine.odyssey.backend.cms.dto.filter.EntityFilterDto;
import ru.softmachine.odyssey.backend.cms.dto.filter.FieldFilterDto;
import ru.softmachine.odyssey.backend.cms.dto.filter.FilterOperator;
import ru.softmachine.odyssey.backend.cms.dto.DictionaryHttpMethod;
import ru.softmachine.odyssey.backend.cms.dto.base.FieldType;
import ru.softmachine.odyssey.backend.cms.entity.base.UidIdentEntity;
import ru.softmachine.odyssey.backend.cms.entity.field.FieldDef;
import ru.softmachine.odyssey.backend.cms.evaluation.spel.SimpleEvaluationProcessor;
import ru.softmachine.odyssey.backend.cms.exception.EntityNotFoundException;
import ru.softmachine.odyssey.backend.cms.provider.EntityStorageProvider;
import ru.softmachine.odyssey.backend.cms.repository.EntityDefRepository;
import ru.softmachine.odyssey.backend.cms.repository.EntityRepository;
import ru.softmachine.odyssey.backend.cms.repository.FieldDefRepository;
import ru.softmachine.odyssey.backend.cms.service.storage.provider.props.ExternalDictionaryProperties;
import ru.softmachine.odyssey.backend.cms.utils.EntityDefUtils;
import ru.softmachine.odyssey.backend.model.UprCmsSlugBaseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestEntityStorageProvider implements EntityStorageProvider {

    private final ExternalDictionaryProperties externalDictionaryProperties;
    private final EntityDefRepository entityDefRepository;
    // для справочников (заведомо собственных сущностей)
    private final EntityRepository entityRepository;
    private final FieldDefRepository fieldDefRepository;
    private final EntityConverter entityConverter;
    private final EntityDefUtils entityDefUtils;
    private final SimpleEvaluationProcessor evaluationService;

    @Value("${app.page.default-page-size}")
    private Integer defaultPageSize;

    @Value("${app.rest-entity-web-client.buffer-size-mb:16}")
    private Integer webClientBufferSizeMb;

    @Override
    public Map<String, Object> getEntityByEntityDefAndSlug(String entityDefCode, String entitySlug) {
        return null;
    }

    @Override
    public UprCmsSlugBaseEntity getBaseSlugEntityByEntityDefAndSlug(String entityDefCode, String entitySlug) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getEntityByEntityDefAndSlugList(String entityDefCode, List<String> entitySlugs) {
        return List.of();
    }

    @Override
    public List<Map<String, Object>> getAllEntityByEntityDefAndIdList(String entityDefCode, List<String> entityIds) {
        return List.of();
    }

    @Override
    public List<UprCmsSlugBaseEntity> getSlugListByEntityDefAndIdList(String entityDefCode, List<String> entityIds) {
        return List.of();
    }

    @Override
    public Map<String, Object> getEntityByEntityDefCode(String entityDefCode, UUID entityId) {
        return null;
    }

    @Override
    public Map<String, Object> getSingletonEntityByEntityDefCode(String entityDefCode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Object> getSingletonEntityByEntityDefId(UUID entityDefId) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EntityDto> getAllEntityByEntityDef(UUID entityDefId, EntityFilterDto filter) {
        var entityDef = entityDefRepository.findById(entityDefId).orElseThrow(() ->
                        new EntityNotFoundException("Entity Def was not found", entityDefId.toString()));

        var context = externalDictionaryProperties.getContext();
        var client = getWebClient(entityDef.getUrlList());

        // строим заголовки запроса
        Consumer<HttpHeaders> headers = httpHeaders ->
                entityDef.getHeaders().forEach(httpHeader -> {
                    try {
                        httpHeaders.add(httpHeader.getName(), Boolean.TRUE.equals(httpHeader.getIsSpel())
                                ? evaluationService.evaluate(httpHeader.getValue(), context, null).toString()
                                : httpHeader.getValue()
                        );
                    } catch (Exception e) {
                        log.warn("Header calculate failed: {}", httpHeader.getName(), e);
                    }
                });

        // если справочник предполагает запрос в GET параметре
        if (entityDef.getMethod() == DictionaryHttpMethod.GET) {
            var res = client.get()
                    .uri(UriBuilder::build)
                    .headers(headers)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            var entities = res.get(entityDef.getResponseParam());
            if (entities instanceof List) {
                new PageImpl((List<?>) entities).stream()
                        .map(entity -> entityConverter.deserializeEntityByMap(entityDef,
                                (Map<String, Object>) entity,
                                null))
                        .toList();
            }
        }

        // если справочник предполагает запрос в теле POST
        if (entityDef.getMethod() == DictionaryHttpMethod.POST) {
            // заполняем тело фильтра
            var map = new HashMap<String, Object>();
            if (entityDef.getDefaultBody() != null) {
                map.putAll(entityDef.getDefaultBody());
            }
            map.putAll(makeFilter(filter));

            // заполнение пагинации
            if (entityDef.getPageFilterName() != null
                    && entityDef.getPageFilterSizeName() != null
                    && entityDef.getPageFilterNumberName() != null) {
                map.put(entityDef.getPageFilterName(), Map.of(
                        entityDef.getPageFilterNumberName(), Optional.ofNullable(filter.getPageNumber()).orElse(0),
                        entityDef.getPageFilterSizeName(), Optional.ofNullable(filter.getPageSize()).orElse(defaultPageSize)
                ));
            }
            var res = client.post()
                    .headers(headers)
                    .body(BodyInserters.fromValue(map))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            var entities = res.get(entityDef.getResponseParam());
            if (entities instanceof List) {
                var total = ((List<?>) entities).size();
                if (res.containsKey(entityDef.getResponseTotalName())
                        && res.get(entityDef.getResponseTotalName()) instanceof Number) {
                    total = ((Number) res.get(entityDef.getResponseTotalName())).intValue();
                }
                return new PageImpl<>(((List<?>) entities).stream()
                        .map(entity -> entityConverter.deserializeEntityByMap(entityDef,
                                (Map<String, Object>) entity, null))
                        .toList(),
                        filter.getAsPage(),
                        total
                );
            }
        }

        return new PageImpl<>(List.of(), filter.getAsPage(), 0);
    }

    private WebClient getWebClient(String baseUrl) {
        var size = (int) DataSize.ofMegabytes(webClientBufferSizeMb).toBytes();
        var strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        return WebClient.builder()
                .exchangeStrategies(strategies)
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public Page<Map<String, Object>> getAllEntityByEntityDefCode(UUID entityDefId, EntityFilterDto filter, Pageable pageable) {
        return new PageImpl<>(List.of());
    }

    @Override
    public List<BaseRef> getAllEntityRefByEntityDefCode(UUID entityDefId, EntityFilterDto filter, Pageable pageable) {
        return List.of();
    }

    @Override
    public EntityDto getEntityById(UUID entityDefId, String entityId) {
        var entityDef = entityDefRepository.findById(entityDefId)
                .orElseThrow(() -> new EntityNotFoundException("Entity def was not found", entityDefId.toString()));

        var client = WebClient.create(entityDef.getUrl());
        var entity = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(entityId)
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return entityConverter.deserializeEntityByMap(
                entityDef,
                (Map<String, Object>) entity,
                this::getEntityById // ни разу не тестил
        );
    }

    @Override
    public void deleteEntityById(UUID entityDefId, String entityId) {

    }

    @Override
    public Map<String, Object> getEntityMapById(UUID entityDefId, String entityId) {
        return Map.of();
    }

    @Override
    public EntityDto saveEntity(UUID entityDefId, EntityDto entityDto) {
        return null;
    }

    @Override
    public EntityDto patchEntity(UUID entityDefId, EntityPatchDto entityDto) {
        return null;
    }

    @Override
    public void afterEntityChange(String entityId) {
        // nothing
    }

    @Override
    public void setSequences(EntitySeqDto entitySeqDto) {
    }

    /**
     * Строит тело запроса для отдельного фильтра
     *
     * @return
     */
    private Object getFilterValue(FieldDef fieldDef, FieldFilterDto fieldFilter) {
        if (fieldDef.getType() == FieldType.STRING) {
            return fieldFilter.getTextValue();
        }
        if (fieldDef.getType() == FieldType.DOUBLE) {
            return fieldFilter.getDoubleValue();
        }
        if (fieldDef.getType() == FieldType.INT) {
            if (fieldDef.isMultiple()) {
                return fieldFilter.getIntArray();
            }
            return fieldFilter.getIntValue();
        }
        if (fieldDef.getType() == FieldType.STAR) {
            if (CollectionUtils.isEmpty(fieldFilter.getIntArray())) {
                return List.of(fieldFilter.getIntValue());
            }
            return fieldFilter.getIntArray();
        }
        if (fieldDef.getType() == FieldType.DICTIONARY_EXTERNAL) {
            if (fieldDef.isMultiple()) {
                return fieldFilter.getExternalDictionaryValues().stream().map(BaseStringRef::getId).toList();
            }
            return fieldFilter.getExternalRefValue().getId();
        }
        if (fieldDef.getType() == FieldType.DICTIONARY) {
            var fieldEnumSerialized = entityDefUtils.getAllFieldDefs(fieldDef.getCollectionRef().getContainer())
                    .filter(fieldDefInner -> Boolean.TRUE.equals(fieldDefInner.getSerializeEnum()))
                    .findFirst();
            if (fieldEnumSerialized.isPresent()) {
                if (!fieldDef.isMultiple()) {
                    return entityConverter.getEnumValue(
                            fieldEnumSerialized.get().getId(),
                            entityRepository.findById(fieldFilter.getRefValue().getId()).orElseThrow(
                                    () -> new EntityNotFoundException("Entity was not found",
                                            fieldFilter.getRefValue().getId().toString()))
                    );
                } else {
                    return fieldFilter.getDictionaryValues().stream()
                            .map(dictionaryRef -> entityConverter.getEnumValue(fieldEnumSerialized.get().getId(),
                                    entityRepository.findById(dictionaryRef.getId()).orElseThrow(
                                            () -> new EntityNotFoundException("Entity was not found",
                                                    dictionaryRef.getId().toString()))))
                            .toList();
                }
            } else {
                throw new IllegalArgumentException("No Field Enum Serialized was found in dictionary with id: " +
                        fieldDef.getCollectionRef().getId());
            }
        }
        return null;
    }

    private String getFilterName(FieldDef fieldDef, FieldFilterDto fieldFilter) {
        return ObjectUtils.firstNonNull(
                (FilterOperator.GT.equals(fieldFilter.getOperator())
                        || FilterOperator.GTE.equals(fieldFilter.getOperator()) ? fieldDef.getExternalFilterLowBoundaryName() : null),
                (FilterOperator.LT.equals(fieldFilter.getOperator())
                        || FilterOperator.LTE.equals(fieldFilter.getOperator()) ? fieldDef.getExternalFilterUpBoundaryName() : null),
                fieldDef.getExternalFilterName(),
                fieldDef.getCode()
        );
    }

    /**
     * Метод строит фильтр для внешнего запроса
     *
     * @param entityFilter
     * @return
     */
    private Map<String, Object> makeFilter(EntityFilterDto entityFilter) {
        var fieldDefIds = entityFilter.getFieldFilters().stream().map(filter -> filter.getFieldDef().getId()).collect(Collectors.toList());
        fieldDefIds.addAll(entityFilter.getSearchFilter().getFields().stream().map(BaseRef::getId).toList());

        var fieldDefIdCodeMap = fieldDefRepository.findAllById(fieldDefIds).stream()
                .collect(Collectors.toMap(
                        UidIdentEntity::getId,
                        Function.identity()
                ));

        var filterMap = entityFilter.getFieldFilters().stream()
                .map(filter -> {
                    var value = getFilterValue(fieldDefIdCodeMap.get(filter.getFieldDef().getId()), filter);
                    var fieldDef = fieldDefIdCodeMap.get(filter.getFieldDef().getId());
                    var filterName = getFilterName(fieldDef, filter);
                    return (value != null && filterName != null) ?
                            Map.entry(
                                    filterName,
                                    value
                            ) :
                            null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (f1, f2) -> f1));

        filterMap.putAll(entityFilter.getSearchFilter().getFields().stream()
                .map(field -> {
                    var value = entityFilter.getSearchFilter().getSearch();
                    var fieldDef = fieldDefIdCodeMap.get(field.getId());
                    var filterName = ObjectUtils.firstNonNull(fieldDef.getExternalFilterName(), fieldDef.getCode());
                    return (value != null && filterName != null) ?
                            Map.entry(
                                    filterName,
                                    value
                            ) :
                            null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (f1, f2) -> f1))
        );

        return filterMap;
    }

    public List<EntityDto> getDumpEntityByEntityDef(
            UUID entityDefId
    ) {
        return List.of();
    }
}
