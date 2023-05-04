package com.rmit.authservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rmit.authservice.dto.AccountDTO;
import com.rmit.authservice.dto.ResponseDTO;
import com.rmit.authservice.entity.UserCredential;
import com.rmit.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addNewUser(@Valid @RequestBody AccountDTO accountDTO) {
        return authService.saveUser(accountDTO);
    }
    @PostMapping("/login")
    public String getToken(@RequestBody AccountDTO userCredential) {
        Authentication authenticate = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userCredential.getEmail(), userCredential.getPassword()));
        if(authenticate.isAuthenticated()){
            return authService.generateToken(userCredential.getEmail());
        }else{
            throw new RuntimeException("User invalid");
        }
    }
    @GetMapping("/validateToken")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }
}
