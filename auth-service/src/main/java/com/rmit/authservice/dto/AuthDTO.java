package com.rmit.authservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rmit.authservice.enums.Permission;
import com.rmit.authservice.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class AuthDTO {
    private Roles role;
    private List<Permission> permissions;
}
