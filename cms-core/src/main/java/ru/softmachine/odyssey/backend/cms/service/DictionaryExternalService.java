package ru.softmachine.odyssey.backend.cms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.softmachine.odyssey.backend.cms.converter.DictionaryExternalConverter;
import ru.softmachine.odyssey.backend.cms.dto.DictionaryExternalDto;
import ru.softmachine.odyssey.backend.cms.dto.base.BaseExternalRef;
import ru.softmachine.odyssey.backend.cms.entity.DictionaryExternal;
import ru.softmachine.odyssey.backend.cms.dto.DictionaryHttpMethod;
import ru.softmachine.odyssey.backend.cms.evaluation.spel.SimpleEvaluationProcessor;
import ru.softmachine.odyssey.backend.cms.exception.EntityNotFoundException;
import ru.softmachine.odyssey.backend.cms.repository.DictionaryExternalRepository;
import ru.softmachine.odyssey.backend.cms.service.storage.provider.props.ExternalDictionaryProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DictionaryExternalService {

    private final ExternalDictionaryProperties externalDictionaryProperties;
    private final DictionaryExternalRepository dictionaryExternalRepository;
    private final DictionaryExternalConverter dictionaryExternalConverter;
    private final SimpleEvaluationProcessor evaluationService;
    private final ObjectMapper objectMapper;

    @Value("${spring.max-in-memory-size}")
    private int maxInMemorySize;

    @Transactional(readOnly = true)
    public List<BaseExternalRef> getDictionaryAuto(UUID dictionaryId, String search) {
        var dictionaryExternal = dictionaryExternalRepository.findById(dictionaryId)
                .orElseThrow(() ->
                        new EntityNotFoundException("External dictionary was not found", dictionaryId.toString()));

        var context = externalDictionaryProperties.getContext();
        var client = createClient(dictionaryExternal, context);

        // строим заголовки запроса
        Consumer<HttpHeaders> headers = httpHeaders ->
                dictionaryExternal.getHeaders().forEach(httpHeader -> {
                    try {
                        var headerValue = httpHeader.getValue();

                        if (Boolean.TRUE.equals(httpHeader.getIsSpel())) {
                            headerValue = evaluationService.evaluate(headerValue, context, null).toString();
                        }

                        httpHeader.setValue(headerValue);
                    } catch (Exception e) {
                        log.warn("Error create header", e);
                    }

                    httpHeaders.add(httpHeader.getName(), httpHeader.getValue());
                });

        // если справочник предполагает запрос в GET параметре
        if (dictionaryExternal.getMethod() == DictionaryHttpMethod.GET) {
            var res = client.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam(dictionaryExternal.getParam().getLast(), search) // для параметра запроса берется последний
                            .build())
                    .headers(headers)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            var items = res.get(dictionaryExternal.getResponseParam());
            if (items instanceof List) {
                return ((List<?>) items).stream()
                        .map(item -> convertToExternalRef(item, dictionaryExternal))
                        .toList();
            }
        }

        // если справочник предполагает запрос в теле POST
        if (dictionaryExternal.getMethod() == DictionaryHttpMethod.POST) {
            Map<String, Object> map = new HashMap<>();
            if (dictionaryExternal.getDefaultBody() != null) {
                map.putAll(dictionaryExternal.getDefaultBody());
            }
            map = setupSearchFieldByPath(map, dictionaryExternal.getParam(), search);

            var res = client.post()
                    .headers(headers)
                    .body(BodyInserters.fromValue(map))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            var items = res.get(dictionaryExternal.getResponseParam());
            if (items instanceof List) {
                return ((List<?>) items).stream()
                        .map(item -> convertToExternalRef(item, dictionaryExternal))
                        .toList();
            }
        }
        return List.of();
    }

    /**
     * Устанавливает по массиву пути строку
     *
     * @param map    тело запроса в виде иерархической карты
     * @param path   массив обозначающий путь до поля с поиском
     * @param search значение поиска
     * @return
     */
    private Map<String, Object> setupSearchFieldByPath(Map<String, Object> map, List<String> path, String search) {
        if (StringUtils.isEmpty(search)) {
            return map;
        }

        if (path.size() > 1) {
            var current = path.getFirst();
            var currentMap = map.getOrDefault(current, null);
            if (!(currentMap instanceof Map)) {
                currentMap = new HashMap<>();
            }
            map.put(current, setupSearchFieldByPath(
                    (Map<String, Object>) currentMap,
                    path.subList(1, path.size()),
                    search)
            );
        } else {
            map.put(path.getFirst(), search);
        }
        return map;
    }

    public List<DictionaryExternalDto> getAllDictionaryExternal() {
        return dictionaryExternalRepository.findAll().stream()
                .map(dictionaryExternalConverter::convertToDto).toList();
    }

    private BaseExternalRef convertToExternalRef(Object item, DictionaryExternal dictionaryExternal) {
        if (item instanceof Map) {
            var res = new BaseExternalRef();
            Map<String, Object> unionContext = new HashMap<>();
            unionContext.putAll(externalDictionaryProperties.getContext());
            unionContext.putAll((Map<String, ?>) item);

            res.setId(Optional.ofNullable(((Map<?, ?>) item).getOrDefault(getIdParamOrDefault(dictionaryExternal), null))
                    .map(Object::toString)
                    .orElse(null));
            res.setName(Optional.ofNullable(((Map<?, ?>) item).getOrDefault(getNameParamOrDefault(dictionaryExternal), null))
                    .map(Object::toString)
                    .orElse(null));
            if (dictionaryExternal.getDescriptionParam() != null) {
                res.setDescription(Optional.ofNullable(((Map<?, ?>) item).getOrDefault(dictionaryExternal.getDescriptionParam(), null))
                        .map(Object::toString)
                        .orElse(null));
            } else {
                if (StringUtils.isNotBlank(dictionaryExternal.getDescriptionExpression())) {
                    try {
                        res.setDescription(
                                evaluationService.evaluate(dictionaryExternal.getDescriptionExpression(),
                                        unionContext).toString()
                        );
                    } catch (Exception e) {
                        log.warn("Error calculates description expression", e);
                    }
                }
            }
            if (dictionaryExternal.getImgParam() != null) {
                res.setImg(Optional.ofNullable(((Map<?, ?>) item).getOrDefault(dictionaryExternal.getImgParam(), null))
                        .map(Object::toString)
                        .orElse(null));
            } else {
                if (StringUtils.isNotBlank(dictionaryExternal.getImgExpression())) {
                    try {
                        res.setImg(
                                evaluationService.evaluate(dictionaryExternal.getImgExpression(),
                                        unionContext).toString()
                        );
                    } catch (Exception e) {
                        log.warn("Error calculates img expression", e);
                    }
                }
            }
            if (StringUtils.isNotBlank(dictionaryExternal.getRefExpression())) {
                try {
                    res.setRef(
                            evaluationService.evaluate(dictionaryExternal.getRefExpression(),
                                    unionContext).toString()
                    );
                } catch (Exception e) {
                    log.warn("Error calculates ref expression", e);
                }
            }
            return res;
        }
        return objectMapper.convertValue(item, BaseExternalRef.class);
    }

    public String getIdParamOrDefault(DictionaryExternal dictionaryExternal) {
        return StringUtils.isNotBlank(dictionaryExternal.getIdParam()) ? dictionaryExternal.getIdParam() : "id";
    }

    public String getNameParamOrDefault(DictionaryExternal dictionaryExternal) {
        return StringUtils.isNotBlank(dictionaryExternal.getNameParam()) ? dictionaryExternal.getNameParam() : "name";
    }

    private WebClient createClient(DictionaryExternal dictionaryExternal, Map<String, Object> context) {
        WebClient client = null;

        try {
            String url = dictionaryExternal.getUrl();
            if (Boolean.TRUE.equals(dictionaryExternal.getIsSpel())) {
                url = evaluationService.evaluate(dictionaryExternal.getUrl(), context).toString();
            }

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
