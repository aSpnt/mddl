package ru.softmachine.odyssey.backend.cms.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.softmachine.odyssey.backend.cms.dto.ValueExternalDto;
import ru.softmachine.odyssey.backend.cms.dto.DictionaryHttpMethod;
import ru.softmachine.odyssey.backend.cms.entity.ExternalConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ValueExternalService {

    private static final String CACHE_NAME = "value_external";

    private final CacheManager cacheManager;

    @Value("${spring.max-in-memory-size}")
    private int maxInMemorySize;

    /**
     * Запрашивает значения пакетно и записывает полученный результат в кеш с составным ключом
     * из идентификатора поля и сущности
     *
     * @param ids
     * @param fieldDefId
     * @param connection
     * @return
     */
    public List<ValueExternalDto> getValuesBatchCache(List<String> ids, UUID fieldDefId, ExternalConnection connection) {
        var values = getValuesBatch(ids, connection);
        values.forEach(value -> {
            try {
                cacheManager.getCache(CACHE_NAME)
                        .put(makeKeyByIdAndFieldDef(value.getId(), fieldDefId), value.getValue());
            } catch (Exception e) {
                log.error("Ошибка пакетного запроса внешнего значения для: {}", fieldDefId.toString(), e);
            }
        });
        return values;
    }

    public Object getValueById(@NotNull String id, UUID fieldDefId) {
        var valueWrapper = cacheManager.getCache(CACHE_NAME)
                .get(makeKeyByIdAndFieldDef(id, fieldDefId));
        return valueWrapper != null ? valueWrapper.get() : null;
    }

    /**
     * Формирует ключ в кеше.
     * Зависит от сущности и поля (может быть несколько полей у одной сущности значения которых кешируются)
     *
     * @param id
     * @param fieldDefId
     * @return
     */
    private String makeKeyByIdAndFieldDef(@NotNull String id, UUID fieldDefId) {
        if (fieldDefId == null) {
            return id;
        }
        return id + '_' + fieldDefId;
    }

    /**
     * TODO: АПИ дожен быть гибче и принимать не только id
     *
     * @param ids
     * @return
     */
    private List<ValueExternalDto> getValuesBatch(List<String> ids, ExternalConnection connection) {

        var client = createClient(connection);

        // если справочник предполагает запрос в теле POST
        if (connection.getMethod() == DictionaryHttpMethod.POST) {
            var map = setupValueByPath(new HashMap<>(), connection.getParam(), ids);

            var res = client.post()
                    .body(BodyInserters.fromValue(map))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            // последний элемент пути предполагается находится в самом объекте, думаю стоит выделить отдельным полем
            var items = getByPath(res, connection.getResponseParam().subList(0, connection.getResponseParam().size() - 1));
            if (items instanceof List) {
                return ((List<?>) items).stream()
                        .map(item -> convertToValueExternal(item, connection))
                        .filter(Objects::nonNull)
                        .toList();
            }
        }
        return List.of();
    }

    private ValueExternalDto convertToValueExternal(Object item, ExternalConnection externalConnection) {
        if (item instanceof Map map) {
            return new ValueExternalDto()
                    .setId(map.getOrDefault("id", "").toString())
                    .setValue(map.getOrDefault(externalConnection.getResponseParam().getLast(), null));
        }
        log.warn("Can't convert object {} to {}", item, ValueExternalDto.class.getName());
        return null;
    }

    /**
     * Устанавливает по массиву пути строку
     *
     * @param map   тело запроса в виде иерархической карты
     * @param path  массив обозначающий путь до поля с поиском
     * @param value значение запроса
     * @return
     */
    private Map<String, Object> setupValueByPath(Map<String, Object> map, List<String> path, Object value) {
        if (path.size() > 1) {
            var current = path.getFirst();
            var currentMap = map.getOrDefault(current, null);
            if (!(currentMap instanceof Map)) {
                currentMap = new HashMap<>();
            }
            map.put(current, setupValueByPath(
                    (Map<String, Object>) currentMap,
                    path.subList(1, path.size()),
                    value)
            );
        } else {
            map.put(path.getFirst(), value);
        }
        return map;
    }

    /**
     * Возвращает значение по пути
     *
     * @param map
     * @param path
     * @return
     */
    private Object getByPath(Map<String, Object> map, List<String> path) {
        if (map == null) {
            return null;
        }
        var currentPathElem = path.getFirst();
        var current = map.getOrDefault(currentPathElem, null);
        if (path.size() == 1) {
            return current;
        }
        if (current instanceof Map) {
            return getByPath((Map<String, Object>) current,
                    path.subList(1, path.size()));
        }
        return null;
    }

    private WebClient createClient(ExternalConnection dictionaryExternal) {
        WebClient client = null;

        try {
            String url = dictionaryExternal.getUrl();

            client = WebClient.builder()
                    .baseUrl(url)
                    .codecs(codecs -> codecs
                            .defaultCodecs()
                            .maxInMemorySize(maxInMemorySize * 1024))
                    .build();
        } catch (Exception e) {
            log.warn("Error create external client", e);
        }
        return client;
    }
}
