package com.example.accountservice.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JWTPayload {
    private String role;
    private List<String> permission;
    private String sub;
    private String iat;
    private String exp;

}
