package com.rmit.product.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.product.dto.AuthDTO;
import com.rmit.product.dto.JWTPayload;
import com.rmit.product.dto.ResponseDTO;
import com.rmit.product.enums.Permission;
import com.rmit.product.feignclients.AuthFeignClient;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Aspect
@Component
@RequiredArgsConstructor
public class RoleAspect {

    private final ObjectMapper objectMapper;
    private final AuthFeignClient authFeignClient;

    @Around(value = "@annotation(auth)")
    public Object role(ProceedingJoinPoint joinPoint, Auth auth) throws Throwable {
        JWTPayload jwtPayload = objectMapper.readValue(joinPoint.getArgs()[0].toString(), JWTPayload.class);
        AuthDTO authDTO = authFeignClient.getAuth(jwtPayload.getSub()).getBody();
        if (ObjectUtils.isEmpty(authDTO)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseDTO<>("Permission denied"));
        if (authDTO.getRole() == auth.role() &&
                checkPermission(authDTO.getPermissions(), auth
                        .permissions())) {
            return joinPoint.proceed();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseDTO<>("Permission denied"));
    }

    public boolean checkPermission(List<Permission> permissions, Permission[] permissionsArr) {
        if (permissionsArr.length < 1) return true;
        Set<Permission> set1 = new HashSet<>(permissions);
        Set<Permission> set2 = new HashSet<>(Arrays.stream(permissionsArr).toList());
        set1.retainAll(set2);
        return !set1.isEmpty();
    }
}
