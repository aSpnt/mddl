package com.aspnt.mddl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aspnt.mddl.service.TagService;

import java.util.List;
import java.util.UUID;

/**
 * Обслуживание специального типа TAGS (теги) требует особенных эндпоинтов для поиска
 * не применимых для других типов. Поэтому появился отдельный контроллер.
 */
@Controller
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @RequestMapping(value = "/api/meta/entity/tag/{field_def_id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<String>> getEntityDefById(
            @PathVariable(name = "field_def_id") UUID fieldDefId,
            @RequestParam(name = "search", required = false) String search
    ) {
        return ResponseEntity.ok(tagService.searchTags(fieldDefId, search));
    }
}
