package com.aspnt.mddl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aspnt.mddl.dto.EntityTemplateDto;
import com.aspnt.mddl.dto.filter.EntityTemplateFilterDto;
import com.aspnt.mddl.service.EntityTemplateService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/api/meta/entity_template")
@RequiredArgsConstructor
public class EntityTemplateController {

    private final EntityTemplateService entityTemplateService;

    @RequestMapping(value = "/by_def/{entity_def_id}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Page<EntityTemplateDto>> getAllEntityTemplateByEntityDef(
            @PathVariable(name = "entity_def_id") UUID entityDefId,
            @Validated @RequestBody EntityTemplateFilterDto filter
    ) {
        return ResponseEntity.ok(entityTemplateService.getEntityTemplatesByEntityDefId(entityDefId, filter));
    }

    @RequestMapping(value = "/by_def/by_def_code/{entity_def_code}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Page<EntityTemplateDto>> getAllEntityTemplateByEntityDefCode(
            @PathVariable(name = "entity_def_code") String entityDefCode,
            @Validated @RequestBody EntityTemplateFilterDto filter
    ) {
        return ResponseEntity.ok(entityTemplateService.getEntityTemplatesByEntityDefCode(entityDefCode, filter));
    }

    @RequestMapping(value = "/by_entity_ref/{entity_id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<EntityTemplateDto>> getAllEntityTemplateByEntityRef(
            @PathVariable(name = "entity_id") UUID entityId
    ) {
        return ResponseEntity.ok(entityTemplateService.getEntityTemplatesByEntityId(entityId));
    }

    @RequestMapping(value = "/by_def_code/{entity_def_code}/{entity_template_id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<EntityTemplateDto> getEntityByIdAndDefCode(
            @PathVariable(name = "entity_def_code") String entityDefCode,
            @PathVariable(name = "entity_template_id") UUID entityTemplateId
    ) {
        return ResponseEntity.ok(
                entityTemplateService.getEntityTemplateById(entityDefCode, entityTemplateId)
        );
    }

    @RequestMapping(value = "/by_def_code/{entity_def_code}/map/{entity_template_id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getEntityTemplateMapByIdAndDefCode(
            @PathVariable(name = "entity_def_code") String entityDefCode,
            @PathVariable(name = "entity_template_id") UUID entityTemplateId
    ) {
        return ResponseEntity.ok(
                entityTemplateService.getEntityTemplateMapById(entityDefCode, entityTemplateId)
        );
    }

    @RequestMapping(value = "/{entity_template_id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteEntityById(
            @PathVariable(name = "entity_template_id") UUID entityTemplateId
    ) {
        entityTemplateService.deleteEntityTemplateById(null, entityTemplateId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/by_def_code/{entity_def_code}/{entity_template_id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteEntityByIdAndDefCode(
            @PathVariable(name = "entity_def_code") String entityDefCode,
            @PathVariable(name = "entity_template_id") UUID entityTemplateId
    ) {
        entityTemplateService.deleteEntityTemplateById(entityDefCode, entityTemplateId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/status/by_def_code/{entity_def_code}/{target_status_code}",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<EntityTemplateDto> statusTransition(
            @RequestBody EntityTemplateDto entityTemplateDto,
            @PathVariable(name = "entity_def_code") String entityDefCode,
            @PathVariable(name = "target_status_code") String targetStatusCode
    ) {
        return ResponseEntity.ok(entityTemplateService.saveEntityTemplate(entityDefCode, entityTemplateDto));
    }

    @RequestMapping(value = "",
            produces = {"application/json"},
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<EntityTemplateDto> saveEntityTemplate(
            @RequestBody EntityTemplateDto entityTemplateDto
    ) {
        return ResponseEntity.ok(entityTemplateService.saveEntityTemplate(null, entityTemplateDto));
    }

    @RequestMapping(value = "/by_def_code/{entity_def_code}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<EntityTemplateDto> saveEntityTemplate(
            @PathVariable(name = "entity_def_code") String entityDefCode,
            @RequestBody EntityTemplateDto entityTemplateDto
    ) {
        return ResponseEntity.ok(
                entityTemplateService.saveEntityTemplate(entityDefCode, entityTemplateDto));
    }
}
