package com.aspnt.mddl.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aspnt.mddl.converter.filter.EntityFilterConverter;
import com.aspnt.mddl.service.entity.EntityService;
import ru.softmachine.odyssey.backend.model.UPRErrorResponse;
import ru.softmachine.odyssey.backend.model.UPRPageFilter;
import ru.softmachine.odyssey.backend.model.UprCmsEntityRequest;
import ru.softmachine.odyssey.backend.model.UprCmsEntityResponse;
import ru.softmachine.odyssey.backend.model.UprCmsIdListRequest;
import ru.softmachine.odyssey.backend.model.UprCmsListBaseEntity;
import ru.softmachine.odyssey.backend.model.UprCmsSlugBaseEntity;
import ru.softmachine.odyssey.backend.model.UprCmsSlugListRequest;
import ru.softmachine.odyssey.backend.services.api.UprCmsApi;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UprEntityController implements UprCmsApi {

    private final EntityService entityService;
    private final EntityFilterConverter entityFilterConverter;

    @Value("${app.page.default-page-size}")
    private Integer defaultPageSize;

    @Operation(
            operationId = "saveEntityByMap",
            summary = "Сохранение сущности из карты значений по коду дефиниции",
            tags = {"upr-cms"},
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Сохраненная сущность",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = Map.class
                            )
                    )}
            ), @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = UPRErrorResponse.class
                            )
                    )}
            )}
    )
    @RequestMapping(value = "/api/cms/{entityDefCode}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveEntityByMap(
            @PathVariable String entityDefCode,
            @RequestBody Map<String, Object> entityMap
    ) {
        return ResponseEntity.ok(entityService.saveEntityByMap(entityDefCode, entityMap));
    }

    @Operation(
            operationId = "saveEntityByMap",
            summary = "Обновление сущности из карты значений по коду дефиниции",
            tags = {"upr-cms"},
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Сохраненная сущность",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = Map.class
                            )
                    )}
            ), @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = UPRErrorResponse.class
                            )
                    )}
            )}
    )
    @RequestMapping(value = "/api/cms/{entityDefCode}/{entityId}",
            produces = {"application/json"},
            method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateEntityByMap(
            @PathVariable String entityDefCode,
            @PathVariable String entityId,
            @RequestBody Map<String, Object> entityMap
    ) {
        return ResponseEntity.ok(entityService.updateEntityByMapAndId(entityDefCode, entityId, entityMap));
    }

    @Operation(
            operationId = "deleteEntityByDefCodeAndId",
            summary = "Удаление сущности по коду дефиниции и идентификатору",
            tags = {"upr-cms"},
            responses = {@ApiResponse(
                    responseCode = "204",
                    description = "Удаление успешно",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = Map.class
                            )
                    )}
            ), @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = UPRErrorResponse.class
                            )
                    )}
            )}
    )
    @RequestMapping(value = "/api/cms/{entityDefCode}/{entityId}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteEntityByDefCodeAndId(
            @PathVariable String entityDefCode,
            @PathVariable String entityId
    ) {
        entityService.deleteEntityByDefCodeAndId(entityDefCode, entityId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UprCmsEntityResponse> findEntities(
            @Validated UprCmsEntityRequest uprCmsEntityRequest
    ) {
        var result = entityService.getAllEntityByEntityDefCode(
                uprCmsEntityRequest.getEntityDefCode(),
                entityFilterConverter.convertToDto(
                        uprCmsEntityRequest.getEntityDefCode(),
                        uprCmsEntityRequest.getSearchFilter(),
                        uprCmsEntityRequest),
                PageRequest.of(
                        Optional.ofNullable(uprCmsEntityRequest.getPageFilter())
                                .map(UPRPageFilter::getPageIndex)
                                .orElse(0),
                        Optional.ofNullable(uprCmsEntityRequest.getPageFilter())
                                .map(UPRPageFilter::getPageSize)
                                .orElse(defaultPageSize)
                        // TODO: ограничение стоит добавить
                )
        );
        return ResponseEntity.ok(
                new UprCmsEntityResponse()
                        .items(result.toList())
                        .totalCount(result.getTotalElements())
        );
    }

    @Override
    public ResponseEntity<UprCmsEntityResponse> findEntitiesByIdList(
            String entityDefCode,
            UprCmsIdListRequest uprCmsIdListRequest
    ) {
        return ResponseEntity.ok(
                new UprCmsEntityResponse()
                        .items(entityService.getAllEntityByEntityDefAndIdList(
                                entityDefCode, uprCmsIdListRequest.getIds()
                        )));
    }

    @Override
    public ResponseEntity<UprCmsEntityResponse> findEntitiesBySlugList(
            String entityDefCode,
            @Validated UprCmsSlugListRequest uprCmsSlugListRequest
    ) {
        return ResponseEntity.ok(
                new UprCmsEntityResponse()
                        .items(entityService.getEntityByEntityDefAndSlugList(
                                entityDefCode, uprCmsSlugListRequest.getSlugs()
                        )));
    }

    @Override
    public ResponseEntity<Map<String, Object>> getEntity(String entityDefCode, String entityId) {
        return ResponseEntity.ok(
                entityService.getEntityByEntityDefCode(
                        entityDefCode, UUID.fromString(entityId)
                ));
    }

    @Operation(
            operationId = "getSingletonEntity",
            summary = "Запрос единственной сущности (синглтона) по коду дефиниции",
            tags = {"upr-cms"},
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Найденная по коду дефиниции сущность",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = Map.class
                            )
                    )}
            ), @ApiResponse(
                    responseCode = "404",
                    description = "Entity не найдена"
            ), @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = UPRErrorResponse.class
                            )
                    )}
            )}
    )
    @RequestMapping(value = "/api/cms/singleton/{entityDefCode}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSingletonEntity(
            @PathVariable String entityDefCode
    ) {
        return ResponseEntity.ok(
                entityService.getSingletonEntityByEntityDefCode(
                        entityDefCode
                ));
    }

    @Override
    public ResponseEntity<Map<String, Object>> getEntityBySlug(String entityDefCode, String entitySlug) {
        return ResponseEntity.ok(
                entityService.getEntityByEntityDefAndSlug(
                        entityDefCode, entitySlug
                ));
    }

    @Override
    public ResponseEntity<UprCmsSlugBaseEntity> getIdEntityBySlug(String entityDefCode, String entitySlug) {
        return ResponseEntity.ok(
                entityService.getBaseSlugEntityByEntityDefAndSlug(entityDefCode, entitySlug)
        );
    }

    @Override
    public ResponseEntity<UprCmsListBaseEntity> getSlugListByIdList(
            String entityDefCode,
            UprCmsIdListRequest uprCmsIdListRequest
    ) {
        return ResponseEntity.ok(
                new UprCmsListBaseEntity().items(
                        entityService.getSlugListByIdList(entityDefCode, uprCmsIdListRequest.getIds())));
    }
}
