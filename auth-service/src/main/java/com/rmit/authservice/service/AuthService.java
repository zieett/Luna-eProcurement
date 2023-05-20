package com.rmit.authservice.service;

import com.rmit.authservice.dto.AccountDTO;
import com.rmit.authservice.dto.ResponseDTO;
import com.rmit.authservice.dto.ResponseTokenDTO;
import com.rmit.authservice.entity.RefreshToken;
import com.rmit.authservice.entity.UserCredential;
import com.rmit.authservice.enums.Roles;
import com.rmit.authservice.feignclients.AccountFeignClient;
import com.rmit.authservice.repository.UserCredentialRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final AccountFeignClient accountFeignClient;
    private final RefreshTokenService refreshTokenService;

    public ResponseEntity<ResponseDTO> saveUser(AccountDTO accountDTO) {
        try {
            HttpStatusCode status;
            if (!userExist(accountDTO.getEmail())) {
                UserCredential userCredential = modelMapper.map(accountDTO, UserCredential.class);
                userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
                ResponseEntity<String> responseEntity = accountFeignClient.createAccount(accountDTO);
                status = responseEntity.getStatusCode();
                log.info("Successfully create user with email: {}", accountDTO.getEmail());
//                userCredential.setRole(Roles.ADMIN);
                userCredentialRepository.save(userCredential);
                return ResponseEntity.ok(new ResponseDTO(LocalDateTime.now(), "Successfully create user", status.value()));
            }
            status = HttpStatus.FOUND;
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseDTO(LocalDateTime.now(), "This user already exist, please use another email address", status.value()));
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(LocalDateTime.now(), "Cannot create user", HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseTokenDTO> getToken(AccountDTO userCredential) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userCredential.getEmail(), userCredential.getPassword()));
            if (authenticate.isAuthenticated()) {
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(userCredential.getEmail());
                ResponseTokenDTO responseTokenDTO =  ResponseTokenDTO.builder()
                        .accessToken(generateToken(userCredential.getEmail()))
                        .refreshToken(refreshToken.getToken())
                        .message("Sign in successfully")
                        .build();
                return ResponseEntity.ok(responseTokenDTO);
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseTokenDTO("Invalid username or password"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseTokenDTO("Internal server error"));
    }

    private boolean userExist(String email) {
        Optional<UserCredential> userCredential = userCredentialRepository.findByEmail(email);
        return userCredential.isPresent();
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}
