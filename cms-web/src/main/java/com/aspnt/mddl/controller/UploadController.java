package com.aspnt.mddl.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.aspnt.mddl.controller.dto.IdResponse;
import com.aspnt.mddl.service.storage.UploadService;

import java.util.UUID;

@Controller
@RequestMapping("/api/meta/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @RequestMapping(
            value = {"/{field_def_id}"},
            produces = {"application/json"},
            consumes = {"multipart/form-data"},
            method = {RequestMethod.POST}
    )
    public ResponseEntity<IdResponse<String>> uploadImage(
            @Valid @RequestPart(value = "file", required = false) MultipartFile file,
            @PathVariable(name = "field_def_id") UUID fieldDefId,
            @RequestParam(required = false) String prefix,
            @RequestParam(required = false) String id
    ) {
        String resultId = uploadService.uploadFile(
                id != null ? id : UUID.randomUUID().toString(),
                fieldDefId,
                prefix,
                file
        );
        return new ResponseEntity<>(new IdResponse<>(resultId), HttpStatus.CREATED);
    }

    @SneakyThrows
    @GetMapping("/**")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadFile(HttpServletRequest request) {
        var key = request.getRequestURI()
                .split(request.getContextPath() + "/api/meta/upload/")[1];

        var fileInfo = uploadService.download(key);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(fileInfo.getContentType()))
                .body(new InputStreamResource(fileInfo.getStream()));
    }
}
