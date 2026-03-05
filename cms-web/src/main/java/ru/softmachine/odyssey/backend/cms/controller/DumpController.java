package com.aspnt.mddl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.aspnt.mddl.dto.export.EntityDefExportEnvelope;
import com.aspnt.mddl.service.DumpService;
import software.amazon.awssdk.utils.StringInputStream;

import java.io.IOException;


/**
 * Обслуживание специального типа TAGS (теги) требует особенных эндпоинтов для поиска
 * не применимых для других типов. Поэтому появился отдельный контроллер.
 */
@Controller
@RequiredArgsConstructor
public class DumpController {

    private final DumpService dumpService;
    private final ObjectMapper objectMapper;

    @RequestMapping(value = "/api/meta/export/{entity_def_code}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> exportEntityDef(
            @PathVariable(name = "entity_def_code") String entityDefCode,
            @RequestParam(name = "includeEntities", required = false, defaultValue = "true") Boolean includeEntities
    ) throws JsonProcessingException {
        var dumpEnvelope = dumpService.getDumpForEntityDef(entityDefCode, includeEntities);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + entityDefCode + ".json");

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .headers(headers)
                .body(new InputStreamResource(
                        new StringInputStream(
                                objectMapper.writeValueAsString(dumpEnvelope))));
    }

    @RequestMapping(value = "/api/meta/import/{entity_def_code}",
            produces = {"application/json"},
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> getEntityDefById(
            @Valid @RequestPart(value = "file", required = true) MultipartFile file,
            @PathVariable(name = "entity_def_code") String entityDefCode
    ) throws IOException {
        var dump = objectMapper.readValue(file.getInputStream(), EntityDefExportEnvelope.class);
        dumpService.importDumpForEntityDef(entityDefCode, dump);

        return ResponseEntity.noContent().build();
    }
}
