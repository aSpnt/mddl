package com.aspnt.mddl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aspnt.mddl.dto.EntityCommentDto;
import com.aspnt.mddl.service.entity.EntityService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/meta/entity_comment")
@RequiredArgsConstructor
public class EntityCommentController {

    private final EntityService entityService;

    @RequestMapping(value = "/{entity_id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<EntityCommentDto>> getAllCommentsByEntityId(
            @PathVariable(name = "entity_id") UUID entityId
    ) {
        return ResponseEntity.ok(entityService.getCommentsByEntityId(entityId));
    }
}
