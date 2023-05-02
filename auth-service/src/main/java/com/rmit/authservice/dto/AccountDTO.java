package com.rmit.authservice.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO implements Serializable {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String gender;
}
