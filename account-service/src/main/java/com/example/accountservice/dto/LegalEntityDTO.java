package com.example.accountservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LegalEntityDTO {
    @NotNull(message = "Legal Entity name must not be null")
    private String name;
    @NotNull(message = "Legal Entity code must not be null")
    private String code;
}
