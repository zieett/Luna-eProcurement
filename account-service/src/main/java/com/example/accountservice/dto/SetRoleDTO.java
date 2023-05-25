package com.example.accountservice.dto;

import com.example.accountservice.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SetRoleDTO {
    @NotNull(message = "Email must not be null")
    private String email;

    @NotNull(message = "Role must not be null")
    @Valid
    private Roles role;
}
