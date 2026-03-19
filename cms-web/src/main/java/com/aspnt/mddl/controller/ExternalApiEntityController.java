package com.aspnt.mddl.controller;

import com.aspnt.mddl.dto.api.ExternalApiEntityRequest;
import com.aspnt.mddl.dto.api.ExternalApiEntityResponse;
import com.aspnt.mddl.dto.api.ExternalApiErrorResponse;
import com.aspnt.mddl.dto.api.ExternalApiIdListRequest;
import com.aspnt.mddl.dto.api.ExternalApiListBaseEntity;
import com.aspnt.mddl.dto.api.ExternalApiPageFilter;
import com.aspnt.mddl.dto.base.BaseSlugRef;
import com.aspnt.mddl.dto.api.ExternalApiSlugListRequest;
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

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ExternalApiEntityController {

    private final EntityService entityService;
    private final EntityFilterConverter entityFilterConverter;

    @Value("${app.page.default-page-size}")
    private Integer defaultPageSize;

    @Operation(
            operationId = "saveEntityByMap",
            summary = "Сохранение сущности из карты значений по коду дефиниции",
            tags = {"mddl"},
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
                                    implementation = ExternalApiErrorResponse.class
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
            tags = {"mddl"},
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
                                    implementation = ExternalApiErrorResponse.class
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
            tags = {"mddl"},
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
                                    implementation = ExternalApiErrorResponse.class
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

    public ResponseEntity<ExternalApiEntityResponse> findEntities(
            @Validated ExternalApiEntityRequest uprCmsEntityRequest
    ) {
        var result = entityService.getAllEntityByEntityDefCode(
                uprCmsEntityRequest.getEntityDefCode(),
                entityFilterConverter.convertToDto(
                        uprCmsEntityRequest.getEntityDefCode(),
                        uprCmsEntityRequest.getSearchFilter(),
                        uprCmsEntityRequest),
                PageRequest.of(
                        Optional.ofNullable(uprCmsEntityRequest.getPageFilter())
                                .map(ExternalApiPageFilter::getPageIndex)
                                .orElse(0),
                        Optional.ofNullable(uprCmsEntityRequest.getPageFilter())
                                .map(ExternalApiPageFilter::getPageSize)
                                .orElse(defaultPageSize)
                        // TODO: ограничение стоит добавить
                )
        );
        return ResponseEntity.ok(
                new ExternalApiEntityResponse()
                        .items(result.toList())
                        .totalCount(result.getTotalElements())
        );
    }

    public ResponseEntity<ExternalApiEntityResponse> findEntitiesByIdList(
            String entityDefCode,
            ExternalApiIdListRequest uprCmsIdListRequest
    ) {
        return ResponseEntity.ok(
                new ExternalApiEntityResponse()
                        .items(entityService.getAllEntityByEntityDefAndIdList(
                                entityDefCode, uprCmsIdListRequest.getIds()
                        )));
    }

    public ResponseEntity<ExternalApiEntityResponse> findEntitiesBySlugList(
            String entityDefCode,
            @Validated ExternalApiSlugListRequest uprCmsSlugListRequest
    ) {
        return ResponseEntity.ok(
                new ExternalApiEntityResponse()
                        .items(entityService.getEntityByEntityDefAndSlugList(
                                entityDefCode, uprCmsSlugListRequest.getSlugs()
                        )));
    }

    public ResponseEntity<Map<String, Object>> getEntity(String entityDefCode, String entityId) {
        return ResponseEntity.ok(
                entityService.getEntityByEntityDefCode(
                        entityDefCode, UUID.fromString(entityId)
                ));
    }

    @Operation(
            operationId = "getSingletonEntity",
            summary = "Запрос единственной сущности (синглтона) по коду дефиниции",
            tags = {"mddl"},
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
                                    implementation = ExternalApiErrorResponse.class
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

    public ResponseEntity<Map<String, Object>> getEntityBySlug(String entityDefCode, String entitySlug) {
        return ResponseEntity.ok(
                entityService.getEntityByEntityDefAndSlug(
                        entityDefCode, entitySlug
                ));
    }

    public ResponseEntity<BaseSlugRef> getIdEntityBySlug(String entityDefCode, String entitySlug) {
        return ResponseEntity.ok(
                entityService.getBaseSlugEntityByEntityDefAndSlug(entityDefCode, entitySlug)
        );
    }

    public ResponseEntity<ExternalApiListBaseEntity> getSlugListByIdList(
            String entityDefCode,
            ExternalApiIdListRequest idListRequest
    ) {
        return ResponseEntity.ok(
                new ExternalApiListBaseEntity().items(
                        entityService.getSlugListByIdList(entityDefCode, idListRequest.getIds())));
    }
}
