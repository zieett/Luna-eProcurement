package com.example.accountservice.controllers;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.feignclients.ProductFeignClient;
import com.example.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @GetMapping("/account")
    public AccountDTO getAccount(){
        return accountService.getAccount();
    }
}
