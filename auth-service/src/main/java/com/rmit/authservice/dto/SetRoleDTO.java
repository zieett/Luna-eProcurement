package com.rmit.authservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rmit.authservice.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SetRoleDTO {
    private String userEmail;
    private Roles role;
}
