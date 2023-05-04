package com.rmit.authservice.service;

import com.rmit.authservice.dto.AccountDTO;
import com.rmit.authservice.entity.UserCredential;
import com.rmit.authservice.enums.Roles;
import com.rmit.authservice.feignclients.AccountFeignClient;
import com.rmit.authservice.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final ModelMapper modelMapper;
    private final AccountFeignClient accountFeignClient;
    public ResponseEntity<String> saveUser(AccountDTO accountDTO){
        try{
            UserCredential userCredential = modelMapper.map(accountDTO,UserCredential.class);
            userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
            HttpStatusCode status = accountFeignClient.createAccount(accountDTO).getStatusCode();
            if(status == HttpStatusCode.valueOf(200)){
                log.info("Successfully create user with email: {}",accountDTO.getEmail());
                userCredential.setRole(Roles.ADMIN);
                userCredentialRepository.save(userCredential);
                return ResponseEntity.ok("Successfully create user");
            }
            log.error("Unsuccessfully create user with email due to cannot connect to account service: {}",accountDTO.getEmail());
            return new ResponseEntity<>("Cannot create user",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e){
            return new ResponseEntity<>("Cannot create user",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String generateToken(String username){
        return jwtService.generateToken(username);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }

}
