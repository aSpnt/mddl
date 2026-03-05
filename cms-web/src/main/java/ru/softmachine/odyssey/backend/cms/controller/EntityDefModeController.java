package com.aspnt.mddl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aspnt.mddl.dto.EntityDefModeDto;
import com.aspnt.mddl.service.EntityDefModeService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/meta/entity_def_mode")
@RequiredArgsConstructor
public class EntityDefModeController {

    private final EntityDefModeService entityDefModeService;

    @RequestMapping(value = "{entityDefId}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<EntityDefModeDto>> getAllEntityDefModes(
            @PathVariable(name = "entityDefId", required = false) UUID entityDefId
    ) {
        return ResponseEntity.ok(entityDefModeService.getEntityDefModesByEntityDefId(entityDefId));
    }

    @RequestMapping(value = "{entity_def_id}",
            produces = { "application/json" },
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<EntityDefModeDto> saveEntityDefMode(
            @PathVariable(name = "entity_def_id") UUID entityDefId,
            @RequestBody EntityDefModeDto entityDefModeDto
    ) {
        return ResponseEntity.ok(entityDefModeService.saveEntityDefMode(entityDefId, entityDefModeDto));
    }

    @RequestMapping(value = "/{entity_def_id}/{entity_def_group_id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<EntityDefModeDto> getEntityDefModeById(
            @PathVariable(name = "entity_def_id") UUID entityDefId,
            @PathVariable(name = "entity_def_group_id") UUID entityDefGroupId
    ) {
        return ResponseEntity.ok(entityDefModeService.getEntityDefGroupById(entityDefId, entityDefGroupId));
    }

    @RequestMapping(value = "/{entity_def_id}/{entity_def_mode_id}",
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteEntityDefModeById(
            @PathVariable(name = "entity_def_id") UUID entityDefId,
            @PathVariable(name = "entity_def_mode_id") UUID entityDefModeId
    ) {
        entityDefModeService.deleteEntityDefGroupById(entityDefId, entityDefModeId);
        return ResponseEntity.noContent().build();
    }
}
