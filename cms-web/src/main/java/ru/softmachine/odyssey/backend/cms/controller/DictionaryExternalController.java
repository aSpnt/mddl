package com.aspnt.mddl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aspnt.mddl.dto.DictionaryExternalDto;
import com.aspnt.mddl.dto.base.BaseExternalRef;
import com.aspnt.mddl.service.DictionaryExternalService;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class DictionaryExternalController {

    private final DictionaryExternalService dictionaryExternalService;

    @RequestMapping(value = "/api/meta/external_dictionary/auto",
            produces = { "application/json" },
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<BaseExternalRef>> getDictionaryExternalAuto(
            @RequestParam(name = "dictionaryId") UUID dictionaryId,
            @RequestParam(name = "search") String search
    ) {
        return ResponseEntity.ok(dictionaryExternalService.getDictionaryAuto(dictionaryId, search));
    }

    @RequestMapping(value = "/api/meta/external_dictionary",
            produces = { "application/json" },
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<DictionaryExternalDto>> getAllExternalDictionary() {
        return ResponseEntity.ok(dictionaryExternalService.getAllDictionaryExternal());
    }
}
