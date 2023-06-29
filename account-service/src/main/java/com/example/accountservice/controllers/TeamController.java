package com.example.accountservice.controllers;

import com.example.accountservice.aspect.Auth;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.dto.TeamDTO;
import com.example.accountservice.entity.Team;
import com.example.accountservice.enums.Roles;
import com.example.accountservice.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping(value = "/team/set-team")
    @Auth(role = Roles.MANAGER)
    public ResponseEntity<ResponseDTO<Team>> setTeam(@RequestHeader("userInfo") String userInfo,@Valid @RequestBody TeamDTO teamDTO) {
        return teamService.setAccountTeam(teamDTO);
    }
    @PostMapping(value = "/team/join-team")
    public ResponseEntity<ResponseDTO<Team>> joinTeam(@RequestHeader("userInfo") String userInfo,@Valid @RequestBody TeamDTO teamDTO) {
        return teamService.joinTeam(userInfo, teamDTO);
    }

    @PostMapping(value = "/team")
    public ResponseEntity<ResponseDTO<Team>> createTeam(@RequestHeader("userInfo") String userInfo,
                                                        @Valid @RequestBody TeamDTO teamDTO) {
        return teamService.createTeam(userInfo, teamDTO);
    }

    @GetMapping(value = "/team")
    @Auth(role = Roles.MANAGER)
    public ResponseEntity<ResponseDTO<Team>> getAllTeamInDepartment(@RequestHeader("userInfo") String userInfo,
                                                                    @Valid @RequestBody TeamDTO teamDTO) {
        return teamService.getAllTeamInDepartment(teamDTO);
    }

    @DeleteMapping(value = "/team/{teamCode}")
    @Auth(role = Roles.MANAGER)
    public ResponseEntity<String> deleteTeam(@PathVariable String teamCode) {
        return teamService.deleteTeam(teamCode);
    }
}
