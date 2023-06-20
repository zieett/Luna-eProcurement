package com.example.accountservice.service.impl;

import com.example.accountservice.dto.JWTPayload;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.dto.TeamDTO;
import com.example.accountservice.entity.Account;
import com.example.accountservice.entity.Team;
import com.example.accountservice.exception.AccountNotFoundException;
import com.example.accountservice.exception.TeamNotFoundException;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.DepartmentRepository;
import com.example.accountservice.repository.TeamRepository;
import com.example.accountservice.service.TeamService;
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
public class TeamServiceImpl implements TeamService {

    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;
    private final TeamRepository teamRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public ResponseEntity<ResponseDTO<Team>> setAccountTeam(TeamDTO teamDTO) {
        Account setTeamAccount = accountRepository.findByEmail(teamDTO.getUserEmail()).orElseThrow(
            () -> new AccountNotFoundException("Cannot find account with email: " + teamDTO.getUserEmail()));
        Team team = teamRepository.findById(teamDTO.getTeamCode()).orElseThrow(
            () -> new TeamNotFoundException("Cannot find team with code:" + teamDTO.getTeamCode()));
        setTeamAccount.setTeamCode(team.getTeamCode());
        accountRepository.save(setTeamAccount);
        return ResponseEntity.ok(new ResponseDTO<>("Succesfully set an department for account" + teamDTO.getUserEmail(),
            HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ResponseDTO<Team>> createTeam(String userInfo, TeamDTO teamDTO) {
        Optional<Team> team = teamRepository.findById(teamDTO.getTeamCode());
        if (team.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO<>("This team code is already exist", HttpStatus.BAD_REQUEST.value()));
        }
        try {
            JWTPayload jwtPayload = objectMapper.readValue(userInfo, JWTPayload.class);
//            Account account = accountRepository.findByEmail(jwtPayload.getSub()).orElseThrow(
//                () -> new AccountNotFoundException("Cannot find account with email: " + jwtPayload.getSub()));
            departmentRepository.findById(teamDTO.getDepartmentCode()).orElseThrow(
                () -> new TeamNotFoundException("Cannot find department with code:" + teamDTO.getDepartmentCode()));
            teamRepository.save(new Team(teamDTO.getTeamCode(), teamDTO.getTeamName(), teamDTO.getDepartmentCode()));
            return ResponseEntity.ok(
                new ResponseDTO<>("Successfully create a team " + teamDTO.getTeamCode(), HttpStatus.OK.value()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<Team>> getAllTeamInDepartment(TeamDTO teamDTO) {
        List<Team> teams = teamRepository.findAllByDepartmentCode(teamDTO.getDepartmentCode());
        if (CollectionUtils.isEmpty(teams)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(
                "Cannot find any team with " + teamDTO.getDepartmentCode() + " department code",
                HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), teams));
    }

    @Override
    public ResponseEntity<ResponseDTO<Team>> joinTeam(String userInfo, TeamDTO teamDTO) {
        try {
            Team team = teamRepository.findById(teamDTO.getTeamCode()).orElseThrow(
                () -> new TeamNotFoundException("Cannot find team with code:" + teamDTO.getTeamCode()));
            JWTPayload jwtPayload = objectMapper.readValue(userInfo, JWTPayload.class);
            Account account = accountRepository.findByEmail(jwtPayload.getSub()).orElseThrow(
                () -> new AccountNotFoundException("Cannot find account with email: " + jwtPayload.getSub()));
            if (account.getTeamCode() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDTO<>("This account is already in a team", HttpStatus.BAD_REQUEST.value()));
            }
            account.setTeamCode(team.getTeamCode());
            accountRepository.save(account);
            return ResponseEntity.ok(new ResponseDTO<>("Succesfully join a team", HttpStatus.OK.value()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}