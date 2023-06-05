package com.example.accountservice.service.impl;

import com.example.accountservice.dto.JWTPayload;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.dto.TeamDTO;
import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.Department;
import com.example.accountservice.entity.Team;
import com.example.accountservice.enums.Roles;
import com.example.accountservice.exception.AccountNotFoundException;
import com.example.accountservice.exception.DepartmentNotFoundException;
import com.example.accountservice.exception.TeamNotFoundException;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.TeamRepository;
import com.example.accountservice.service.TeamService;
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
public class TeamServiceImpl implements TeamService {
    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;
    private final TeamRepository teamRepository;

    @Override
    public ResponseEntity<ResponseDTO<Team>> setAccountTeam(String userInfo, TeamDTO teamDTO) {
        try {
            JWTPayload jwtPayload = objectMapper.readValue(userInfo, JWTPayload.class);
            Account account = accountRepository.findByEmail(jwtPayload.getSub()).orElseThrow(() -> new AccountNotFoundException("Cannot find account with email: "+ jwtPayload.getSub()));
            if(account.getRole() != Roles.MANAGER)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO<>("You are not permission to set a department for a user", HttpStatus.UNAUTHORIZED.value()));
            Account setTeamAccount = accountRepository.findByEmail(teamDTO.getEmail()).orElseThrow(() -> new AccountNotFoundException("Cannot find account with email: "+ teamDTO.getEmail()));
            Team department = teamRepository.findById(teamDTO.getCode()).orElseThrow(() -> new TeamNotFoundException("Cannot find team with code:" + teamDTO.getCode()));
            setTeamAccount.setDepartmentCode(department.getCode());
            accountRepository.save(setTeamAccount);
            return ResponseEntity.ok(new ResponseDTO("Succesfully set an department for account" + teamDTO.getEmail(), HttpStatus.OK.value()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<Team>> createTeam(String userInfo, TeamDTO teamDTO) {
        try {
            JWTPayload jwtPayload = objectMapper.readValue(userInfo, JWTPayload.class);
            Account account = accountRepository.findByEmail(jwtPayload.getSub()).orElseThrow(() -> new AccountNotFoundException("Cannot find account with email: "+ jwtPayload.getSub()));
            if(account.getRole() != Roles.MANAGER)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO<>("You are not permission to create a team", HttpStatus.UNAUTHORIZED.value()));
            if(teamRepository.findById(teamDTO.getCode()).isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>("This team code is already exist",HttpStatus.BAD_REQUEST.value()));
            }
            teamRepository.save(new Team(teamDTO.getCode(),teamDTO.getName(),account.getDepartmentCode()));
            return ResponseEntity.ok(new ResponseDTO("Successfully create a team " + teamDTO.getCode(), HttpStatus.OK.value()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<Team>> getAllTeamInDepartment(String userInfo) {
        try {
            JWTPayload jwtPayload = objectMapper.readValue(userInfo, JWTPayload.class);
            Account account = accountRepository.findByEmail(jwtPayload.getSub()).orElseThrow(() -> new AccountNotFoundException("Cannot find account with email: "+ jwtPayload.getSub()));
            if(account.getRole() != Roles.MANAGER)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO<>("You are not permission to view all team", HttpStatus.UNAUTHORIZED.value()));
            List<Team> teams = teamRepository.findAllByDepartmentCode(account.getDepartmentCode());
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(),teams));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<Team>> joinTeam(String userInfo, TeamDTO teamDTO) {
        try{
            JWTPayload jwtPayload = objectMapper.readValue(userInfo, JWTPayload.class);
            Account account = accountRepository.findByEmail(jwtPayload.getSub()).orElseThrow(() -> new AccountNotFoundException("Cannot find account with email: " +teamDTO.getEmail()));
            account.setDepartmentCode(teamDTO.getCode());
            accountRepository.save(account);
            return ResponseEntity.ok(new ResponseDTO<>("Succesfully join a team",HttpStatus.OK.value()));
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
