package com.example.accountservice.aspect;

import com.example.accountservice.dto.JWTPayload;
import com.example.accountservice.dto.ResponseDTO;
import com.example.accountservice.entity.Account;
import com.example.accountservice.exception.AccountNotFoundException;
import com.example.accountservice.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RoleAspect {

    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;

    @Around(value = "@annotation(role)")
    public Object role(ProceedingJoinPoint joinPoint, Role role) throws Throwable {
        JWTPayload jwtPayload = objectMapper.readValue(joinPoint.getArgs()[0].toString(), JWTPayload.class);
        Account account = accountRepository.findByEmail(jwtPayload.getSub())
            .orElseThrow(() -> new AccountNotFoundException("Cannot find account with email: " + jwtPayload.getSub()));
        if (account.getRole() != role.value()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseDTO<>("Permission denied", HttpStatus.UNAUTHORIZED.value()));
        }
        return joinPoint.proceed();
    }

}
