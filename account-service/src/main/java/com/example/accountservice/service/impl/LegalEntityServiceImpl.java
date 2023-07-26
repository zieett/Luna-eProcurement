package com.example.accountservice.service.impl;

import com.example.accountservice.dto.*;
import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.Department;
import com.example.accountservice.entity.LegalEntity;
import com.example.accountservice.entity.Team;
import com.example.accountservice.enums.Roles;
import com.example.accountservice.exception.AccountNotFoundException;
import com.example.accountservice.exception.DepartmentNotFoundException;
import com.example.accountservice.exception.LegalEntityNotFoundException;
import com.example.accountservice.exception.TeamNotFoundException;
import com.example.accountservice.feignclients.AuthFeignClient;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.DepartmentRepository;
import com.example.accountservice.repository.LegalEntityRepository;
import com.example.accountservice.repository.TeamRepository;
import com.example.accountservice.service.LegalEntityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class LegalEntityServiceImpl implements LegalEntityService {

    private final LegalEntityRepository legalEntityRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;
    private final TeamRepository teamRepository;
    private final DepartmentRepository departmentRepository;
    private final AuthFeignClient authFeignClient;

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
            SetRoleDTO setRoleDTO = new SetRoleDTO(jwtPayload.getSub(), Roles.MANAGER);
            authFeignClient.setRole(setRoleDTO);
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
                        .orElseThrow(() -> new TeamNotFoundException("Cannot find team")).getName());
            }
            if (!Objects.isNull(accountDTO.getDepartmentCode())) {
                accountDTO.setTeamName(departmentRepository.findById(accountDTO.getDepartmentCode())
                        .orElseThrow(() -> new DepartmentNotFoundException("Cannot find department")).getName());
            }
            AuthDTO authDTO = authFeignClient.getAuth(a.getEmail()).getBody();
            accountDTO.setRole(authDTO.getRole());
            accountDTO.setPermissions(authDTO.getPermissions());
            return accountDTO;
        }).toList();
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), accountDTOS));
    }

    @Override
    public ResponseEntity<ResponseDTO<LegalEntityInfoDTO>> findLegalEntityInfo(String entityCode) {
        LegalEntity legalEntity = legalEntityRepository.findByCode(entityCode)
                .orElseThrow(() -> new LegalEntityNotFoundException("Cannot find legal entity with code: " + entityCode));
        LegalEntityInfoDTO legalEntityInfoDTO = modelMapper.map(legalEntity, LegalEntityInfoDTO.class);
        List<Department> departments = departmentRepository.getDepartmentByLegalEntityCode(
                legalEntityInfoDTO.getCode());
        List<DepartmentInfoDTO> departmentInfoDTOS = departments.stream()
                .map(department -> modelMapper.map(department, DepartmentInfoDTO.class)).toList();
        departmentInfoDTOS.stream().forEach(departmentInfoDTO -> {
            departmentInfoDTO.setTeams(getTeamInfo(departmentInfoDTO.getCode()));
        });
        legalEntityInfoDTO.setDepartments(departmentInfoDTOS);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), List.of(legalEntityInfoDTO)));
    }

    @Override
    public ResponseEntity<String> deleteEntity(String entityCode) {
        LegalEntity legalEntity = legalEntityRepository.findByCode(entityCode)
                .orElseThrow(() -> new LegalEntityNotFoundException("Cannot find legal entity with code: " + entityCode));
        List<Account> accounts = accountRepository.findByLegalEntityCode(entityCode);
        List<Department> departments = departmentRepository.findByLegalEntityCode(entityCode);
        //Delete all team in departments
        departments.forEach(department -> teamRepository.deleteAll(teamRepository.findAllByDepartmentCode(department.getCode())));
        //Delete all departments
        departmentRepository.deleteAll(departments);
        //Set all account in legalentity to null
        accounts.forEach(account -> account.setLegalEntityCode(null));
        accountRepository.saveAll(accounts);
        //Delete legal entity
        legalEntityRepository.delete(legalEntity);
        return ResponseEntity.ok("Legal entity deleted");
    }

    @Override
    public ResponseEntity<String> deleteUserInEntity(String entityCode, String userEmail) {
        LegalEntity legalEntity = legalEntityRepository.findByCode(entityCode)
                .orElseThrow(() -> new LegalEntityNotFoundException("Cannot find legal entity with code: " + entityCode));
        Account account = accountRepository.findByEmailAndLegalEntityCode(userEmail, entityCode)
                .orElseThrow(() -> new AccountNotFoundException("Cannot find account with email: " + userEmail + " in legal entity: " + legalEntity.getName()));
        authFeignClient.deleteAccount(userEmail);
        accountRepository.delete(account);
        return ResponseEntity.ok("Account deleted");
    }

    @Override
    public LegalEntity getLegalEntityByCode(String entityCode) {
        return legalEntityRepository.findFirstByCode(entityCode);
    }

    public List<TeamInfo> getTeamInfo(String departmentCode) {
        List<Team> teams = teamRepository.findAllByDepartmentCode(departmentCode);
        return teams.stream().map(team -> modelMapper.map(team, TeamInfo.class)).toList();
    }
}
