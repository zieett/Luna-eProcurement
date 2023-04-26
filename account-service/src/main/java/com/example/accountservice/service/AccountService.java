package com.example.accountservice.service;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.entity.Account;
import com.example.accountservice.feignclients.ProductFeignClient;
import com.example.accountservice.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final ProductFeignClient productFeignClient;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    public AccountDTO getAccount(Long id){
        ObjectMapper objectMapper = new ObjectMapper();
        Account account = accountRepository.findById(id).get();
        log.info("Account: {}",account);
        AccountDTO accountDTO = modelMapper.map(account,AccountDTO.class);
        log.info("Accountdto: {}",accountDTO);
        accountDTO.setProductDTO(productFeignClient.getProduct());
        return accountDTO;
    }
    public List<Account> getAccounts(){
        return accountRepository.findAll();
    }
    public Account createAccount(Account account){
        return accountRepository.save(account);
    }
}
