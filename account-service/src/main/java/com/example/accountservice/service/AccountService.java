package com.example.accountservice.service;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.dto.JoinEntityDTO;
import com.example.accountservice.dto.ResponseDTO;
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

public interface AccountService {
    AccountDTO getAccount(Long id);
    AccountDTO getAccountByEmail(String email);
    List<Account> getAccounts();
    ResponseEntity<String> createAccount(@Valid AccountDTO accountDTO);

    ResponseEntity<ResponseDTO> joinEntity(String userInfo,JoinEntityDTO joinEntityDTO);
}
