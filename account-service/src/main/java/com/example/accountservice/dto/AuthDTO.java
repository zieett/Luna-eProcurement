package com.example.accountservice.dto;

import com.example.accountservice.enums.Permission;
import com.example.accountservice.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class AuthDTO {
    private Roles role;
    private List<Permission> permissions;
}
