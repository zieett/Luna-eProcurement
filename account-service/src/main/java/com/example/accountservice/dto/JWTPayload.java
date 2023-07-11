package com.example.accountservice.dto;

import com.example.accountservice.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class JWTPayload {
    private String sub;
    private String iat;
    private String exp;
    private String username;
    private Roles role;
}
