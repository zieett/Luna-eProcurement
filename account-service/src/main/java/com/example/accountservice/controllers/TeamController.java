package com.example.accountservice.controllers;

import com.example.accountservice.dto.DepartmentDTO;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.dto.TeamDTO;
import com.example.accountservice.entity.Department;
import com.example.accountservice.entity.Team;
import com.example.accountservice.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class TeamController {
    private final TeamService teamService;
    @PostMapping(value = "/team/set-team", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<Team>> setTeam(@RequestHeader("userInfo") String userInfo,@Valid @RequestBody TeamDTO teamDTO) {
        return teamService.setAccountTeam(userInfo,teamDTO);
    }
    @PostMapping(value = "/team/join-team", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<Team>> joinTeam(@RequestHeader("userInfo") String userInfo,@Valid @RequestBody TeamDTO teamDTO) {
        return teamService.joinTeam(userInfo,teamDTO);
    }
}
