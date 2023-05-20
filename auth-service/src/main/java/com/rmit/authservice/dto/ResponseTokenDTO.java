package com.rmit.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseTokenDTO {
    private String message;
    private String accessToken;
    private String refreshToken;
    public ResponseTokenDTO(String message){
        this.message = message;
    }
}
