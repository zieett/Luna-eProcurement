package com.rmit.authservice.controllers;

import com.rmit.authservice.dto.*;
import com.rmit.authservice.entity.RefreshToken;
import com.rmit.authservice.service.AuthService;
import com.rmit.authservice.service.JWTService;
import com.rmit.authservice.service.RefreshTokenService;
import com.rmit.authservice.service.UserCredentialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/get-auth")
    public ResponseEntity<AuthDTO> getAuth(@RequestBody String userEmail) {
        return authService.getAuth(userEmail);
    }

    @PostMapping("/set-role")
    public ResponseEntity<String> setRole(@RequestBody SetRoleDTO setRoleDTO) {
        return authService.setRole(setRoleDTO);
    }

    @DeleteMapping("/delete-account/{userEmail}")
    public ResponseEntity<String> deleteAccount(@PathVariable String userEmail) {
        return authService.deleteAccount(userEmail);
    }
}
