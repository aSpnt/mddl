package com.aspnt.mddl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aspnt.mddl.dto.EntityDefGroupDto;
import com.aspnt.mddl.service.EntityDefGroupService;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class EntityDefGroupController {

    private final EntityDefGroupService entityDefGroupService;

    @RequestMapping(value = "/api/meta/entity_def_group",
            produces = { "application/json" },
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<EntityDefGroupDto>> getAllEntityDefGroup(
            @RequestParam(name = "parentId", required = false) UUID parentId
    ) {
        return ResponseEntity.ok(entityDefGroupService.getEntityDefGroupsByParentId(parentId));
    }

    @RequestMapping(value = "/api/meta/entity_def_group",
            produces = { "application/json" },
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<EntityDefGroupDto> saveEntityDefGroup(
            @RequestBody EntityDefGroupDto entityDefGroupDto
    ) {
        return ResponseEntity.ok(entityDefGroupService.saveEntityDefGroup(entityDefGroupDto));
    }

    @RequestMapping(value = "/api/meta/entity_def_group/{entity_def_group_id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<EntityDefGroupDto> getEntityDefGroupById(
            @PathVariable(name = "entity_def_group_id") UUID entityDefGroupId
    ) {
        return ResponseEntity.ok(entityDefGroupService.getEntityDefGroupById(entityDefGroupId));
    }

    @RequestMapping(value = "/api/meta/entity_def_group/{entity_def_group_id}",
            produces = { "application/json" },
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteEntityDefGroupById(
            @PathVariable(name = "entity_def_group_id") UUID entityDefGroupId
    ) {
        entityDefGroupService.deleteEntityDefGroupById(entityDefGroupId);
        return ResponseEntity.noContent().build();
    }
}
