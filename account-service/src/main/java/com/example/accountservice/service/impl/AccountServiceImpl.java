package com.example.accountservice.service.impl;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.dto.JoinEntityDTO;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.LegalEntity;
import com.example.accountservice.exception.AccountNotFoundException;
import com.example.accountservice.exception.LegalEntityNotFoundExeption;
import com.example.accountservice.feignclients.ProductFeignClient;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.LegalEntityRepository;
import com.example.accountservice.service.AccountService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
@RequiredArgsConstructor
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final ProductFeignClient productFeignClient;
    private final AccountRepository accountRepository;
    private final LegalEntityRepository legalEntityRepository;
    private final ModelMapper modelMapper;
    public AccountDTO getAccount(Long id){
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Cannot find account with id: "+ id));
        return modelMapper.map(account,AccountDTO.class);
    }
    public AccountDTO getAccountByEmail(String email){
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new AccountNotFoundException("Cannot find account with email: "+ email));
        return modelMapper.map(account,AccountDTO.class);
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

    @Override
    public ResponseEntity<ResponseDTO> joinEntity(JoinEntityDTO joinEntityDTO) {
        Account account = accountRepository.findByEmail(joinEntityDTO.getAccountEmail()).orElseThrow(() -> new AccountNotFoundException("Cannot find account with email: "+ joinEntityDTO.getAccountEmail()));
        LegalEntity legalEntity = legalEntityRepository.findByCode(joinEntityDTO.getLegalEntityCode()).orElseThrow(() -> new LegalEntityNotFoundExeption("Cannot find legal entity with code: "+ joinEntityDTO.getLegalEntityCode()));
        account.setLegalEntityCode(legalEntity.getCode());
        accountRepository.save(account);
        return ResponseEntity.ok(new ResponseDTO("Succesfully join an entity", HttpStatus.OK.value()));
    }
}
