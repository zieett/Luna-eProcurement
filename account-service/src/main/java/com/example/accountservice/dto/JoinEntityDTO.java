package com.example.accountservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinEntityDTO {
    @NotNull(message = "Account id must not be null")
    private String accountEmail;
    @NotNull(message = "Legal Entity id must not be null")
    private String legalEntityCode;
}
