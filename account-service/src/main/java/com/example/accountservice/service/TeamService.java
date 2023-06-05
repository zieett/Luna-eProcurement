package com.example.accountservice.service;

import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.dto.TeamDTO;
import com.example.accountservice.entity.Team;
import org.springframework.http.ResponseEntity;

public interface TeamService {
    ResponseEntity<ResponseDTO<Team>> joinTeam(String userInfo,TeamDTO teamDTO);
    ResponseEntity<ResponseDTO<Team>> setAccountTeam(String userInfo, TeamDTO teamDTO);
    ResponseEntity<ResponseDTO<Team>> createTeam(String userInfo, TeamDTO teamDTO);
    ResponseEntity<ResponseDTO<Team>> getAllTeamInDepartment(String userInfo);

}
