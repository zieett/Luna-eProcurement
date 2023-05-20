package com.rmit.authservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rmit.authservice.dto.AccountDTO;
import com.rmit.authservice.dto.RefreshTokenRequest;
import com.rmit.authservice.dto.ResponseDTO;
import com.rmit.authservice.dto.ResponseTokenDTO;
import com.rmit.authservice.entity.RefreshToken;
import com.rmit.authservice.entity.UserCredential;
import com.rmit.authservice.service.AuthService;
import com.rmit.authservice.service.JWTService;
import com.rmit.authservice.service.RefreshTokenService;
import com.rmit.authservice.service.UserCredentialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JWTService jwtService;
    private final UserCredentialService userCredentialService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addNewUser(@Valid @RequestBody AccountDTO accountDTO) {
        return authService.saveUser(accountDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseTokenDTO> getToken(@RequestBody AccountDTO userCredential) {
        return authService.getToken(userCredential);
    }
    @GetMapping("/validateToken")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }
    @PostMapping("/refreshToken")
    public ResponseTokenDTO refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserId)
                .map(userId -> {
                    String userEmail = userCredentialService.getUserById(userId).getEmail();
                    String accessToken = jwtService.generateToken(userEmail);
                    return ResponseTokenDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequest.getToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));
    }

}
