package com.example.accountservice.controllers;

import com.example.accountservice.aspect.Auth;
import com.example.accountservice.aspect.Role;
import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.dto.JoinEntityDTO;
import com.example.accountservice.dto.LegalEntityDTO;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.dto.SetRoleDTO;
import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.LegalEntity;
import com.example.accountservice.enums.Roles;
import com.example.accountservice.service.AccountService;
import com.example.accountservice.service.LegalEntityService;
import jakarta.validation.Valid;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final LegalEntityService legalEntityService;

    //    @GetMapping("/account/{id}")
//    public AccountDTO getAccount(@PathVariable Long id) {
//        return accountService.getAccount(id);
//    }
    @GetMapping("/account/{email}")
    public AccountDTO getAccountByEmail(@PathVariable String email) {
        return accountService.getAccountByEmail(email);
    }

    @GetMapping("/account")
    public AccountDTO getAccount(@RequestHeader("userInfo") String userInfo) {
        return accountService.getAccountByUserInfo(userInfo);
    }

    @GetMapping("/accounts")
    @Auth(role = Roles.MANAGER)
    public ResponseEntity<List<Account>> getAccounts(@RequestHeader("userInfo") String userInfo) {
        return accountService.getAccounts();
    }

    @GetMapping("/account/{account-id}/legal-entities")
    public ResponseEntity<List<LegalEntity>> getLegalEnties(@PathVariable(name = "account-id") Long accountId) {
        return legalEntityService.getLegalEntitiesAccount(accountId);
    }

    @PostMapping("/entity/create-entity")
    public ResponseEntity<ResponseDTO> createLegalEntity(@RequestHeader("userInfo") String userInfo,
        @Valid @RequestBody LegalEntityDTO legalEntityDTO) {
        return legalEntityService.createLegalEntity(userInfo, legalEntityDTO);
    }

    @PostMapping("/entity/join-entity")
    public ResponseEntity<ResponseDTO> joinLegalEntity(@RequestHeader("userInfo") String userInfo,
        @Valid @RequestBody JoinEntityDTO joinEntityDTO) {
        return accountService.joinEntity(userInfo, joinEntityDTO);
    }

    @GetMapping("/entity")
    public ResponseEntity<ResponseDTO<LegalEntity>> getAllLegalEntity() {
        return legalEntityService.getAllLegalEntity();
    }

    @PostMapping(value = "/account", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        return accountService.createAccount(accountDTO);
    }

    @GetMapping(value = "/entity/{entityCode}/account")
    @Auth(role = Roles.MANAGER)
    public ResponseEntity<ResponseDTO<AccountDTO>> getAccountInEntity(@RequestHeader("userInfo") String userInfo,
                                                                      @PathVariable(name = "entityCode") String entityCode) {
        return legalEntityService.getAllAccountInEntity(entityCode);
    }

    @PostMapping(value = "/account/set-role")
    @Auth(role = Roles.MANAGER)
    public ResponseEntity<ResponseDTO<String>> setAccountRole(@RequestHeader("userInfo") String userInfo,
                                                              @Valid @RequestBody SetRoleDTO setRoleDTO) {
        return accountService.setAccountRole(setRoleDTO);
    }

    @DeleteMapping(value = "/account/{userEmail}")
    public ResponseEntity<String> deleteAccount(@RequestHeader("userInfo") String userInfo, @PathVariable(name = "userEmail") String userEmail) {
        return accountService.deleteAccount(userEmail);
    }
}
