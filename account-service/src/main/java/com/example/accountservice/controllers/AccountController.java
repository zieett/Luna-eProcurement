package com.example.accountservice.controllers;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.entity.Account;
import com.example.accountservice.feignclients.ProductFeignClient;
import com.example.accountservice.service.AccountService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @GetMapping("/account/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountService.getAccount(id);
    }
    @GetMapping("/accounts")
    public List<Account> getAccounts(){
        return accountService.getAccounts();
    }
    @PostMapping("/account")
    public Account createAccount(@RequestBody Account accountDTO){
        return accountService.createAccount(accountDTO);
    }
}
