package com.example.accountservice.service.impl;

import com.example.accountservice.dto.DepartmentDTO;
import com.example.accountservice.dto.JWTPayload;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.Department;
import com.example.accountservice.entity.LegalEntity;
import com.example.accountservice.enums.Roles;
import com.example.accountservice.exception.AccountNotFoundException;
import com.example.accountservice.exception.DepartmentNotFoundException;
import com.example.accountservice.exception.LegalEntityNotFoundException;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.DepartmentRepository;
import com.example.accountservice.service.DepartmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public ResponseEntity<ResponseDTO<Department>> joinDepartment(String userInfo, DepartmentDTO departmentDTO) {
        try{
            JWTPayload jwtPayload = objectMapper.readValue(userInfo, JWTPayload.class);
            Account account = accountRepository.findByEmail(jwtPayload.getSub()).orElseThrow(() -> new AccountNotFoundException("Cannot find account with email: " +departmentDTO.getEmail()));
            account.setDepartmentCode(departmentDTO.getCode());
            accountRepository.save(account);
            return ResponseEntity.ok(new ResponseDTO<>("Succesfully join a department",HttpStatus.OK.value()));
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<Department>> setAccountDepartment(String userInfo, DepartmentDTO departmentDTO) {
        try {
            JWTPayload jwtPayload = objectMapper.readValue(userInfo, JWTPayload.class);
            Account account = accountRepository.findByEmail(jwtPayload.getSub()).orElseThrow(() -> new AccountNotFoundException("Cannot find account with email: "+ jwtPayload.getSub()));
            if(account.getRole() != Roles.MANAGER)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO<>("You are not permission to set a department for a user", HttpStatus.UNAUTHORIZED.value()));
            Account setDepartmentAccount = accountRepository.findByEmail(departmentDTO.getEmail()).orElseThrow(() -> new AccountNotFoundException("Cannot find account with email: "+ departmentDTO.getEmail()));
            Department department = departmentRepository.findById(departmentDTO.getCode()).orElseThrow(() -> new DepartmentNotFoundException("Cannot find department with code:" + departmentDTO.getCode()));
            setDepartmentAccount.setDepartmentCode(department.getCode());
            accountRepository.save(setDepartmentAccount);
            return ResponseEntity.ok(new ResponseDTO("Successfully set an department for account" + departmentDTO.getEmail(), HttpStatus.OK.value()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<Department>> createDepartment(String userInfo, DepartmentDTO departmentDTO) {
        try {
            JWTPayload jwtPayload = objectMapper.readValue(userInfo, JWTPayload.class);
            Account account = accountRepository.findByEmail(jwtPayload.getSub()).orElseThrow(() -> new AccountNotFoundException("Cannot find account with email: "+ jwtPayload.getSub()));
            if(departmentRepository.findById(departmentDTO.getCode()).isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>("This department code is already exist",HttpStatus.BAD_REQUEST.value()));
            }
            departmentRepository.save(new Department(departmentDTO.getCode(),departmentDTO.getName(),account.getLegalEntityCode()));
            return ResponseEntity.ok(new ResponseDTO("Successfully create a department" + departmentDTO.getCode(), HttpStatus.OK.value()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<Department>> getAllDepartmentInLegalEntity(String userInfo) {
        try {
            JWTPayload jwtPayload = objectMapper.readValue(userInfo, JWTPayload.class);
            Account account = accountRepository.findByEmail(jwtPayload.getSub()).orElseThrow(() -> new AccountNotFoundException("Cannot find account with email: "+ jwtPayload.getSub()));
            if(account.getRole() != Roles.MANAGER)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO<>("You are not permission to view all department", HttpStatus.UNAUTHORIZED.value()));
            List<Department> departmentList = departmentRepository.getDepartmentByLegalEntityCode(account.getLegalEntityCode());
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(),departmentList));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
