package com.example.accountservice.service;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.dto.JoinEntityDTO;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.dto.SetRoleDTO;
import com.example.accountservice.entity.Account;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    AccountDTO getAccount(Long id);

    AccountDTO getAccountByEmail(String email);

    ResponseEntity<List<Account>> getAccounts();

    ResponseEntity<String> createAccount(@Valid AccountDTO accountDTO);

    ResponseEntity<ResponseDTO> joinEntity(String userInfo, JoinEntityDTO joinEntityDTO);

    ResponseEntity<ResponseDTO<AccountDTO>> setAccountRole(SetRoleDTO setRoleDTO);
}
