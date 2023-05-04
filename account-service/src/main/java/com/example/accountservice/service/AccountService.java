package com.example.accountservice.service;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.entity.Account;
import com.example.accountservice.exception.AccountNotFoundException;
import com.example.accountservice.feignclients.ProductFeignClient;
import com.example.accountservice.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import java.nio.channels.AlreadyConnectedException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final ProductFeignClient productFeignClient;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    public AccountDTO getAccount(Long id){
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Cannot find account with id: "+ id));
        AccountDTO accountDTO = modelMapper.map(account,AccountDTO.class);
        return accountDTO;
    }
    public List<Account> getAccounts(){
        List<Account> accounts = accountRepository.findAll();
        if(CollectionUtils.isEmpty(accounts)) throw new AccountNotFoundException("Cannot find any account");
        return accountRepository.findAll();
    }
    public ResponseEntity<String> createAccount(@Valid AccountDTO accountDTO){
        Account account = modelMapper.map(accountDTO,Account.class);
        accountRepository.save(account);
        return ResponseEntity.ok("Account create successfully");
    }
}
