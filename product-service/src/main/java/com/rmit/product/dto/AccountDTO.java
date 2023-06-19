package com.rmit.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rmit.product.enums.Permission;
import com.rmit.product.enums.Roles;
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
    private String departmentCode;
    private String teamCode;
    private List<Permission> permissions;
}
