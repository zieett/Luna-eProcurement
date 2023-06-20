package com.example.accountservice.service.impl;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.dto.JWTPayload;
import com.example.accountservice.dto.LegalEntityDTO;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.LegalEntity;
import com.example.accountservice.enums.Roles;
import com.example.accountservice.exception.AccountNotFoundException;
import com.example.accountservice.exception.DepartmentNotFoundException;
import com.example.accountservice.exception.LegalEntityNotFoundException;
import com.example.accountservice.exception.TeamNotFoundException;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.DepartmentRepository;
import com.example.accountservice.repository.LegalEntityRepository;
import com.example.accountservice.repository.TeamRepository;
import com.example.accountservice.service.LegalEntityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
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
public class LegalEntityServiceImpl implements LegalEntityService {

    private final LegalEntityRepository legalEntityRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;
    private final TeamRepository teamRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public ResponseEntity<List<LegalEntity>> getLegalEntitiesAccount(Long accountId) {
        return ResponseEntity.ok(legalEntityRepository.findAll());
    }

    @Override
    public ResponseEntity<ResponseDTO> createLegalEntity(String userInfo, LegalEntityDTO legalEntityDTO) {
        try {
            JWTPayload jwtPayload = objectMapper.readValue(userInfo, JWTPayload.class);
            Account account = accountRepository.findByEmail(jwtPayload.getSub()).orElseThrow(
                () -> new AccountNotFoundException("Cannot find account with email: " + jwtPayload.getSub()));
            LegalEntity legalEntity = modelMapper.map(legalEntityDTO, LegalEntity.class);
            legalEntityRepository.save(legalEntity);
            account.setLegalEntityCode(legalEntity.getCode());
            account.setRole(Roles.MANAGER);
            accountRepository.save(account);
            return ResponseEntity.ok(
                new ResponseDTO("Successfully create and join legal entity", HttpStatus.OK.value()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<LegalEntity>> getAllLegalEntity() {
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        if (CollectionUtils.isEmpty(legalEntityList)) {
            throw new LegalEntityNotFoundException("Cannot find any legal entity");
        }
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), legalEntityList));
    }

    @Override
    public ResponseEntity<ResponseDTO<AccountDTO>> getAllAccountInEntity(String entityCode) {
        List<Account> accounts = accountRepository.findByLegalEntityCode(entityCode);
        if (CollectionUtils.isEmpty(accounts)) {
            throw new AccountNotFoundException("Cannot find any account in this entity: " + entityCode);
        }
        List<AccountDTO> accountDTOS = accounts.stream().map(a -> {
            AccountDTO accountDTO = modelMapper.map(a, AccountDTO.class);
            if (!Objects.isNull(accountDTO.getTeamCode())) {
                accountDTO.setTeamName(teamRepository.findById(accountDTO.getTeamCode())
                    .orElseThrow(() -> new TeamNotFoundException("Cannot find team")).getTeamName());
            }
            if (!Objects.isNull(accountDTO.getDepartmentCode())) {
                accountDTO.setTeamName(departmentRepository.findById(accountDTO.getDepartmentCode())
                    .orElseThrow(() -> new DepartmentNotFoundException("Cannot find department")).getDepartmentName());
            }
            return accountDTO;
        }).toList();
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), accountDTOS));
    }
}
