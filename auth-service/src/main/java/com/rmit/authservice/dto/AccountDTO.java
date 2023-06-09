package com.rmit.authservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Email(message = "Email is not valid")
    private String email;
    @NotNull(message = "Password must not be null")
    private String password;
    @NotNull(message = "Username must not be null")
    private String username;
//    @NotNull(message = "Phone number must not be null")
//    private String phoneNumber;
//    @NotNull(message = "Gender must not be null")
//    private String gender;
}
