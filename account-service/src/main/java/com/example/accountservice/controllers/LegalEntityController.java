package com.example.accountservice.controllers;

import com.example.accountservice.dto.LegalEntityInfoDTO;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.service.LegalEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LegalEntityController {

    private final LegalEntityService legalEntityService;

    @GetMapping(value = "/entity/{entityCode}/info")
    public ResponseEntity<ResponseDTO<LegalEntityInfoDTO>> getLegalEntityInfo(@PathVariable String entityCode) {
        return legalEntityService.findLegalEntityInfo(entityCode);
    }
}
