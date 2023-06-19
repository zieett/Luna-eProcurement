package com.rmit.product.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.product.dto.AccountDTO;
import com.rmit.product.dto.JWTPayload;
import com.rmit.product.enums.Permission;
import com.rmit.product.feignclients.AccountFeignClient;
import java.util.List;
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
    private final AccountFeignClient accountFeignClient;

    @Around(value = "@annotation(auth)")
    public Object role(ProceedingJoinPoint joinPoint, Auth auth) throws Throwable {
        JWTPayload jwtPayload = objectMapper.readValue(joinPoint.getArgs()[0].toString(), JWTPayload.class);
        AccountDTO account = accountFeignClient.getAccountByEmail(jwtPayload.getSub());
        account.setPermissions(List.of(Permission.EDIT));
        account.setPermissions(List.of(Permission.VIEW));
        if (account.getRole() == auth.value().role() &&
            checkPermission(account.getPermissions(), auth.value()
                .permissions())) {
            return joinPoint.proceed();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body("Permission denied");
    }

    public boolean checkPermission(List<Permission> permissions, Permission[] permissionsArr) {
        return true;
//        permissions.stream()(permission -> {
//            for (Permission p : permissionsArr
//            ) {
//                if (permission == p) {
//                    return true;
//                }
//
//            }
//            return false;
//        });
    }

}
