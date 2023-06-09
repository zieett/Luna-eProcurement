package com.example.accountservice.dto;

import com.example.accountservice.enums.Permission;
import com.example.accountservice.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDTO implements Serializable {
    private Long id;
    @NotNull(message = "Email must not be null")
    @Email
    private String email;
    @NotNull(message = "Username must not be null")
    private String username;
    private String legalEntityCode;
    private Roles role;
    private List<Permission> permissions;
    private String departmentCode;
    private String departmentName;
    private String teamCode;
    private String teamName;
}
