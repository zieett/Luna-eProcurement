package com.example.accountservice.controllers;

import com.example.accountservice.dto.LegalEntityInfoDTO;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.service.LegalEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LegalEntityController {

    private final LegalEntityService legalEntityService;

    @GetMapping(value = "/entity/{entityCode}/info")
    public ResponseEntity<ResponseDTO<LegalEntityInfoDTO>> getLegalEntityInfo(@PathVariable String entityCode) {
        return legalEntityService.findLegalEntityInfo(entityCode);
    }

    @DeleteMapping(value = "/entity/{entityCode}")
    public ResponseEntity<String> deleteEntity(@PathVariable String entityCode) {
        return legalEntityService.deleteEntity(entityCode);
    }
    @DeleteMapping(value = "/entity/{entityCode}/{userEmail}")
    public ResponseEntity<String> deleteUserInEntity(@PathVariable String entityCode, @PathVariable String userEmail) {
        return legalEntityService.deleteUserInEntity(entityCode,userEmail);
    }
}
