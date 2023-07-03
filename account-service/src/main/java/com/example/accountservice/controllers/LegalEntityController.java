package com.example.accountservice.controllers;

import com.example.accountservice.aspect.Auth;
import com.example.accountservice.dto.LegalEntityInfoDTO;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.entity.LegalEntity;
import com.example.accountservice.enums.Roles;
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
    @Auth(role = Roles.MANAGER)
    public ResponseEntity<String> deleteUserInEntity(@RequestHeader String userInfo, @PathVariable String entityCode, @PathVariable String userEmail) {
        return legalEntityService.deleteUserInEntity(entityCode, userEmail);
    }

    @GetMapping(value = "/entity/{entityCode}")
    public LegalEntity getLegalEntityByCode(@PathVariable String entityCode) {
        return legalEntityService.getLegalEntityByCode(entityCode);
    }
}
