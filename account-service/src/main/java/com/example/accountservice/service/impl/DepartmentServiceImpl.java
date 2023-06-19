package com.example.accountservice.service.impl;

import com.example.accountservice.dto.DepartmentDTO;
import com.example.accountservice.dto.JWTPayload;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.Department;
import com.example.accountservice.exception.AccountNotFoundException;
import com.example.accountservice.exception.DepartmentNotFoundException;
import com.example.accountservice.exception.LegalEntityNotFoundException;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.DepartmentRepository;
import com.example.accountservice.repository.LegalEntityRepository;
import com.example.accountservice.service.DepartmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;
    private final DepartmentRepository departmentRepository;
    private final LegalEntityRepository legalEntityRepository;

    @Override
    public ResponseEntity<ResponseDTO<Department>> joinDepartment(String userInfo, DepartmentDTO departmentDTO) {
        try {
            Department department = departmentRepository.findById(departmentDTO.getCode()).orElseThrow(
                () -> new DepartmentNotFoundException("Cannot find department with code:" + departmentDTO.getCode()));
            JWTPayload jwtPayload = objectMapper.readValue(userInfo, JWTPayload.class);
            Account account = accountRepository.findByEmail(jwtPayload.getSub()).orElseThrow(
                () -> new AccountNotFoundException("Cannot find account with email: " + jwtPayload.getSub()));
            if (account.getDepartmentCode() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDTO<>("This account is already in a department", HttpStatus.BAD_REQUEST.value()));
            }
            account.setDepartmentCode(department.getCode());
            accountRepository.save(account);
            return ResponseEntity.ok(new ResponseDTO<>("Succesfully join a department", HttpStatus.OK.value()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<Department>> setAccountDepartment(DepartmentDTO departmentDTO) {
        Account setDepartmentAccount = accountRepository.findByEmail(departmentDTO.getUserEmail()).orElseThrow(
            () -> new AccountNotFoundException("Cannot find account with email: " + departmentDTO.getUserEmail()));
        Department department = departmentRepository.findById(departmentDTO.getCode()).orElseThrow(
            () -> new DepartmentNotFoundException("Cannot find department with code:" + departmentDTO.getCode()));
        setDepartmentAccount.setDepartmentCode(department.getCode());
        accountRepository.save(setDepartmentAccount);
        return ResponseEntity.ok(
            new ResponseDTO<>("Successfully set an department for account" + departmentDTO.getUserEmail(),
                HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ResponseDTO<Department>> createDepartment(String userInfo, DepartmentDTO departmentDTO) {
        try {
            Optional<Department> department = departmentRepository.findById(departmentDTO.getCode());
            if (department.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO<>("This department code is already exist", HttpStatus.BAD_REQUEST.value()));
            }
            JWTPayload jwtPayload = objectMapper.readValue(userInfo, JWTPayload.class);
            legalEntityRepository.findByCode(departmentDTO.getLegalEntityCode()).orElseThrow(
                () -> new LegalEntityNotFoundException(
                    "Cannot find legal entity with code: " + departmentDTO.getLegalEntityCode()));
            departmentRepository.save(
                new Department(departmentDTO.getCode(), departmentDTO.getName(), departmentDTO.getLegalEntityCode()));
            return ResponseEntity.ok(
                new ResponseDTO<>("Successfully create a department" + departmentDTO.getCode(), HttpStatus.OK.value()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<Department>> getAllDepartmentInLegalEntity(DepartmentDTO departmentDTO) {
        List<Department> departmentList = departmentRepository.getDepartmentByLegalEntityCode(
            departmentDTO.getLegalEntityCode());
        if (CollectionUtils.isEmpty(departmentList)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(
                "Cannot find any department with " + departmentDTO.getLegalEntityCode() + " legal entity",
                HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), departmentList));
    }
}
