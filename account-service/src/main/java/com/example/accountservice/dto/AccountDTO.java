package com.example.accountservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO implements Serializable {
    @NotNull(message = "Email must not be null")
    @Email
    private String email;
    @NotNull(message = "Name must not be null")
    private String name;
    @NotNull(message = "Phone number must not be null")
    private String phoneNumber;
    @NotNull(message = "Gender must not be null")
    private String gender;
}
