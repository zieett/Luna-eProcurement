package com.example.accountservice.controllers;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.dto.JWTPayload;
import com.example.accountservice.dto.JoinEntityDTO;
import com.example.accountservice.dto.LegalEntityDTO;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.LegalEntity;
import com.example.accountservice.feignclients.ProductFeignClient;
import com.example.accountservice.service.AccountService;
import com.example.accountservice.service.LegalEntityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AccountController {
    private final AccountService accountService;
    private final LegalEntityService legalEntityService;

    @GetMapping("/account/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @GetMapping("/account")
    public AccountDTO getAccount(@RequestHeader("userInfo") String userInfo) throws JsonProcessingException {
        JWTPayload jwtPayload = new ObjectMapper().readValue(userInfo, JWTPayload.class);
        return accountService.getAccountByEmail(jwtPayload.getSub());
    }

    @GetMapping("/accounts")
    public List<Account> getAccounts(@RequestHeader("userInfo") String userInfo) throws JsonProcessingException {
        System.out.println("UserInfo: " + userInfo);
//        JWTPayload jwtPayload = new ObjectMapper().readValue(userInfo,JWTPayload.class);
//        System.out.println("JWT paylaod: " + jwtPayload.toString());
        return accountService.getAccounts();
    }

    @GetMapping("/account/{account-id}/legal-entities")
    public ResponseEntity<List<LegalEntity>> getLegalEnties(@PathVariable(name = "account-id") Long accountId) {
        return legalEntityService.getLegalEntitiesAccount(accountId);
    }

    @PostMapping("/entity/create-entity")
    public ResponseEntity<ResponseDTO> createLegalEntity(@Valid @RequestBody LegalEntityDTO legalEntityDTO) {
        return legalEntityService.createLegalEntity(legalEntityDTO);
    }

    @PostMapping("/entity/join-entity")
    public ResponseEntity<ResponseDTO> joinLegalEntity(@Valid @RequestBody JoinEntityDTO joinEntityDTO) {
        return accountService.joinEntity(joinEntityDTO);
    }

    @GetMapping("/entity")
    public ResponseEntity<ResponseDTO<LegalEntity>> getAllLegalEntity() {
        return legalEntityService.getAllLegalEntity();
    }

    @PostMapping(value = "/account", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        return accountService.createAccount(accountDTO);
    }
}
