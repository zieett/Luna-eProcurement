package com.example.accountservice.service.impl;

import com.example.accountservice.dto.*;
import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.LegalEntity;
import com.example.accountservice.enums.Roles;
import com.example.accountservice.exception.AccountNotFoundException;
import com.example.accountservice.exception.LegalEntityNotFoundException;
import com.example.accountservice.feignclients.AuthFeignClient;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.LegalEntityRepository;
import com.example.accountservice.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final LegalEntityRepository legalEntityRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final AuthFeignClient authFeignClient;

    public AccountDTO getAccount(Long id) {
        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException("Cannot find account with id: " + id));
        return modelMapper.map(account, AccountDTO.class);
    }

    public AccountDTO getAccountByEmail(String email) {
        Account account = accountRepository.findByEmail(email)
            .orElseThrow(
                () -> new AccountNotFoundException("Cannot find account with email: " + email));
        return modelMapper.map(account, AccountDTO.class);

    }

    public ResponseEntity<List<Account>> getAccounts() {
        List<Account> accounts = accountRepository.findAll();
        if (CollectionUtils.isEmpty(accounts)) {
            throw new AccountNotFoundException("Cannot find any account");
        }
        return ResponseEntity.ok(accountRepository.findAll());

    }

    public ResponseEntity<String> createAccount(@Valid AccountDTO accountDTO) {
        Account account = modelMapper.map(accountDTO, Account.class);
        accountRepository.save(account);
        return ResponseEntity.ok("Account create successfully");
    }

    @Override
    public ResponseEntity<ResponseDTO> joinEntity(String userInfo, JoinEntityDTO joinEntityDTO) {
        try {
            JWTPayload jwtPayload = objectMapper.readValue(userInfo, JWTPayload.class);
            Account account = accountRepository.findByEmail(jwtPayload.getSub()).orElseThrow(
                    () -> new AccountNotFoundException("Cannot find account with email: " + jwtPayload.getSub()));
            LegalEntity legalEntity = legalEntityRepository.findByCode(joinEntityDTO.getLegalEntityCode()).orElseThrow(
                    () -> new LegalEntityNotFoundException(
                            "Cannot find legal entity with code: " + joinEntityDTO.getLegalEntityCode()));
            account.setLegalEntityCode(legalEntity.getCode());
            SetRoleDTO setRoleDTO = new SetRoleDTO(jwtPayload.getSub(), Roles.MEMBER);
            authFeignClient.setRole(setRoleDTO);
            accountRepository.save(account);
            return ResponseEntity.ok(new ResponseDTO("Succesfully join an entity", HttpStatus.OK.value()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<String>> setAccountRole(SetRoleDTO setRoleDTO) {
        Account setRoleAccount = accountRepository.findByEmail(setRoleDTO.getUserEmail()).orElseThrow(
                () -> new AccountNotFoundException("Cannot find account with email: " + setRoleDTO.getUserEmail()));
        authFeignClient.setRole(setRoleDTO);
        return ResponseEntity.ok(
                new ResponseDTO<>("Succesfully set role for an account:  " + setRoleAccount.getEmail(),
                        HttpStatus.OK.value()));
    }

    @Override
    public AccountDTO getAccountByUserInfo(String userInfo) {
        try {
            JWTPayload jwtPayload = objectMapper.readValue(userInfo, JWTPayload.class);
            Account account = accountRepository.findByEmail(jwtPayload.getSub()).orElseThrow(
                    () -> new AccountNotFoundException("Cannot find account with email: " + jwtPayload.getSub()));
            AccountDTO accountDTO = objectMapper.convertValue(account, AccountDTO.class);
            AuthDTO auth = authFeignClient.getAuth(jwtPayload.getSub()).getBody();
            if (!Objects.isNull(auth)) {
                accountDTO.setRole(auth.getRole());
            }
            return accountDTO;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<String> deleteAccount(String userEmail) {
        accountRepository.findByEmail(userEmail).orElseThrow(() -> new AccountNotFoundException("Cannot find user with email: " + userEmail));
        accountRepository.deleteAccountByEmail(userEmail);
        authFeignClient.deleteAccount(userEmail);
        return ResponseEntity.ok("Successfully delete a user");
    }

    ;
}
