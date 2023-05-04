package com.example.accountservice.controllers;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.dto.JWTPayload;
import com.example.accountservice.entity.Account;
import com.example.accountservice.feignclients.ProductFeignClient;
import com.example.accountservice.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class AccountController {
    private final AccountService accountService;
    @GetMapping("/account/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountService.getAccount(id);
    }
    @GetMapping("/accounts")
    public List<Account> getAccounts(@RequestHeader("userInfo") String userInfo ) throws JsonProcessingException {
        System.out.println("UserInfo: "+ userInfo);
        JWTPayload jwtPayload = new ObjectMapper().readValue(userInfo,JWTPayload.class);
        System.out.println("JWT paylaod: " + jwtPayload.toString());
        return accountService.getAccounts();
    }
    @PostMapping(value = "/account",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAccount(@Valid @RequestBody AccountDTO accountDTO){
        return accountService.createAccount(accountDTO);
    }
}
