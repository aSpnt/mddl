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
import com.aspnt.mddl.config.HasActions;
import com.aspnt.mddl.dto.entity.EntityDto;
import com.aspnt.mddl.dto.entity.EntityPatchDto;
import com.aspnt.mddl.dto.EntitySeqListDto;
import com.aspnt.mddl.dto.entity.EntityWithCommentDto;
import com.aspnt.mddl.dto.filter.EntityFilterDto;
import com.aspnt.mddl.service.entity.EntityService;
import com.aspnt.mddl.service.TransitionService;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/api/meta/entity")
@RequiredArgsConstructor
public class EntityController {

    private final EntityService entityService;
    private final TransitionService transitionService;

    @RequestMapping(value = "/by_def/{entity_def_id}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    @ResponseBody
    @HasActions(HasActions.Action.READ)
    public ResponseEntity<Page<EntityDto>> getAllEntityByDef(
            @PathVariable(name = "entity_def_id") UUID entityDefId,
            @Validated @RequestBody EntityFilterDto filter
    ) {
        return ResponseEntity.ok(entityService.getAllEntityByEntityDef(
                entityDefId,
                filter
        ));
    }

    @RequestMapping(value = "/by_def/by_def_code/{entity_def_code}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    @ResponseBody
    @HasActions(HasActions.Action.READ)
    public ResponseEntity<Page<EntityDto>> getAllEntityByDefCode(
            @PathVariable(name = "entity_def_code") String entityDeCode,
            @Validated @RequestBody EntityFilterDto filter
    ) {
        return ResponseEntity.ok(entityService.getAllEntityByEntityDefCode(
                entityDeCode,
                filter
        ));
    }

    // TODO: будет передаваться еще и контекст
    @RequestMapping(value = "/{entity_def_id}/empty/{entity_template_id}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    @ResponseBody
    @HasActions(HasActions.Action.READ)
    public ResponseEntity<EntityDto> createEmptyEntityByTemplateAndDefaults(
            @PathVariable(name = "entity_def_id") UUID entityDefId,
            @PathVariable(name = "entity_template_id") UUID entityTemplateId,
            @RequestBody Map<String, Object> context
    ) {
        return ResponseEntity.ok(entityService.getEmptyEntity(context, entityDefId, entityTemplateId));
    }

    @RequestMapping(value = "/by_def_code/{entity_def_code}/empty/{entity_template_id}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    @ResponseBody
    @HasActions(HasActions.Action.CREATE)
    public ResponseEntity<EntityDto> createEmptyEntityByDefCodeByTemplateAndDefaults(
            @PathVariable(name = "entity_def_code") String entityDefCode,
            @PathVariable(name = "entity_template_id") UUID entityTemplateId,
            @RequestBody Map<String, Object> context
    ) {
        return ResponseEntity.ok(entityService.getEmptyEntityByDefCode(context, entityDefCode, entityTemplateId));
    }

    @RequestMapping(value = "/{entity_def_id}/empty",
            produces = {"application/json"},
            method = RequestMethod.POST)
    @ResponseBody
    @HasActions(HasActions.Action.CREATE)
    public ResponseEntity<EntityDto> createEmptyEntityByDefaults(
            @PathVariable(name = "entity_def_id") UUID entityDefId,
            @RequestBody Map<String, Object> context
    ) {
        return ResponseEntity.ok(entityService.getEmptyEntity(context, entityDefId, null));
    }

    @RequestMapping(value = "/by_def_code/{entity_def_code}/empty",
            produces = {"application/json"},
            method = RequestMethod.POST)
    @ResponseBody
    @HasActions(HasActions.Action.READ)
    public ResponseEntity<EntityDto> createEmptyEntityByDefCodeByDefaults(
            @PathVariable(name = "entity_def_code") String entityDefCode,
            @RequestBody Map<String, Object> context
    ) {
        return ResponseEntity.ok(entityService.getEmptyEntityByDefCode(context, entityDefCode, null));
    }

    @RequestMapping(value = "/{entity_def_id}/{entity_id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @ResponseBody
    @HasActions(HasActions.Action.READ)
    public ResponseEntity<EntityDto> getEntityById(
            @PathVariable(name = "entity_def_id") UUID entityDefId,
            @PathVariable(name = "entity_id") String entityId
    ) {
        return ResponseEntity.ok(entityService.getEntityById(entityDefId, entityId));
    }

    @RequestMapping(value = "/by_def_code/{entity_def_code}/{entity_id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @ResponseBody
    @HasActions(HasActions.Action.READ)
    public ResponseEntity<EntityDto> getEntityByDefCode(
            @PathVariable(name = "entity_def_code") String entityDefCode,
            @PathVariable(name = "entity_id") String entityId
    ) {
        return ResponseEntity.ok(entityService.getEntityByDefCodeById(entityDefCode, entityId));
    }

    @RequestMapping(value = "/{entity_def_id}/{entity_id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    @ResponseBody
    @HasActions(HasActions.Action.DELETE)
    public ResponseEntity<Void> deleteEntityById(
            @PathVariable(name = "entity_def_id") UUID entityDefId,
            @PathVariable(name = "entity_id") String entityId
    ) {
        entityService.deleteEntityById(entityDefId, entityId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/by_def_code/{entity_def_code}/{entity_id}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    @ResponseBody
    @HasActions(HasActions.Action.DELETE)
    public ResponseEntity<Void> deleteEntityByDefCodeAndId(
            @PathVariable(name = "entity_def_code") String entityDefCode,
            @PathVariable(name = "entity_id") String entityId
    ) {
        entityService.deleteEntityByDefCodeAndId(entityDefCode, entityId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{entity_def_id}/map/{entity_id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @ResponseBody
    @HasActions(HasActions.Action.READ)
    public ResponseEntity<Map<String, Object>> getEntityMapById(
            @PathVariable(name = "entity_def_id") UUID entityDefId,
            @PathVariable(name = "entity_id") String entityId
    ) {
        return ResponseEntity.ok(entityService.getEntityMapById(entityDefId, entityId));
    }

    @RequestMapping(value = "/by_def_code/{entity_def_code}/map/{entity_id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @ResponseBody
    @HasActions(HasActions.Action.READ)
    public ResponseEntity<Map<String, Object>> getEntityMapByDefCodeById(
            @PathVariable(name = "entity_def_code") String entityDefCode,
            @PathVariable(name = "entity_id") String entityId
    ) {
        return ResponseEntity.ok(entityService.getEntityMapByDefCodeById(entityDefCode, entityId));
    }

    @RequestMapping(value = "/by_def_code/{entity_def_code}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    @ResponseBody
    @HasActions(value = {HasActions.Action.CREATE, HasActions.Action.UPDATE})
    public ResponseEntity<EntityDto> saveEntityByDefCode(
            @RequestBody EntityWithCommentDto entityDto,
            @PathVariable(name = "entity_def_code") String entityDefCode
    ) {
        return ResponseEntity.ok(entityService.saveEntity(entityDto, entityDefCode));
    }

    @RequestMapping(value = "",
            produces = {"application/json"},
            method = RequestMethod.POST)
    @ResponseBody
    @HasActions(value = {HasActions.Action.CREATE, HasActions.Action.UPDATE})
    public ResponseEntity<EntityDto> saveEntity(
            @RequestBody EntityWithCommentDto entityDto
    ) {
        return ResponseEntity.ok(entityService.saveEntity(entityDto, null));
    }

    @RequestMapping(value = "",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    @ResponseBody
    @HasActions(HasActions.Action.STATUS)
    public ResponseEntity<EntityDto> patchEntity(
            @RequestBody EntityPatchDto entityDto
    ) {
        return ResponseEntity.ok(entityService.patchEntity(entityDto));
    }

    @RequestMapping(value = "/by_def_code/{entity_def_code}",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    @ResponseBody
    @HasActions(HasActions.Action.STATUS)
    public ResponseEntity<EntityDto> patchEntityByDefCode(
            @PathVariable(name = "entity_def_code") String entityDefCode,
            @RequestBody EntityPatchDto entityDto
    ) {
        return ResponseEntity.ok(entityService.patchEntity(entityDto));
    }


    @RequestMapping(value = "/status/{entity_def_code}/{field_value_transition_id}/{target_status_code}",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    @ResponseBody
    @HasActions(HasActions.Action.STATUS)
    public ResponseEntity<EntityDto> statusTransition(
            @RequestBody EntityPatchDto entityDto,
            @PathVariable(name = "entity_def_code") String entityDefCode,
            @PathVariable(name = "field_value_transition_id") UUID fieldValueTransitionId,
            @PathVariable(name = "target_status_code") String targetStatusCode
    ) {
        var res = entityService.patchEntity(entityDto);
        // фиксируем дату изменения статуса, если установлен флаг в transition
        var transition = transitionService.getFieldValueTransition(fieldValueTransitionId);
        if (transition.getUpdateLastStatusTs()) {
            entityService.updateLastTsStatusChange(UUID.fromString(entityDto.getId()),
                    transition.getEntityActiveStatus());
        } else {
            // TODO: сброс не очевидное поведение, нужно от него избавится
            entityService.resetLastTsStatusChange(UUID.fromString(entityDto.getId()),
                    transition.getEntityActiveStatus());
        }
        return ResponseEntity.ok(res);
    }

    @RequestMapping(value = "/seq",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    @ResponseBody
    @HasActions(HasActions.Action.UPDATE)
    public ResponseEntity<EntityDto> setSequences(
            @RequestBody EntitySeqListDto seqListDto
    ) {
        entityService.setSequences(seqListDto);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/by_def_code/{entity_def_code}/seq",
            produces = {"application/json"},
            method = RequestMethod.PATCH)
    @ResponseBody
    @HasActions(HasActions.Action.UPDATE)
    public ResponseEntity<EntityDto> setSequencesBydefCode(
            @RequestBody EntitySeqListDto seqListDto
    ) {
        entityService.setSequences(seqListDto);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/by_map/{entity_def_id}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    @ResponseBody
    @HasActions(HasActions.Action.READ)
    public ResponseEntity<EntityDto> getEntityByEntityDefIdAndMap(
            @PathVariable(name = "entity_def_id") UUID entityDefId,
            @RequestBody Map<String, Object> entityMap
    ) {
        return ResponseEntity.ok(entityService.getEntityByMap(entityDefId, entityMap));
    }

    @RequestMapping(value = "/by_def_code/by_map/{entity_def_code}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    @ResponseBody
    @HasActions(HasActions.Action.READ)
    public ResponseEntity<EntityDto> getEntityByEntityDefCodeAndMap(
            @PathVariable(name = "entity_def_code") String entityDefCode,
            @RequestBody Map<String, Object> entityMap
    ) {
        return ResponseEntity.ok(entityService.getEntityByMap(entityDefCode, entityMap));
    }
}
