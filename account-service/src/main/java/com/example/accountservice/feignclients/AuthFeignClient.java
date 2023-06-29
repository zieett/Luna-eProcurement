package com.example.accountservice.feignclients;

import com.example.accountservice.dto.AuthDTO;
import com.example.accountservice.dto.SetRoleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "auth-service/auth")
public interface AuthFeignClient {
    @PostMapping("/get-auth")
    ResponseEntity<AuthDTO> getAuth(@RequestBody String userEmail);

    @PostMapping("/set-role")
    ResponseEntity<String> setRole(@RequestBody SetRoleDTO setRoleDTO);

    @DeleteMapping("/delete-account/{userEmail}")
    ResponseEntity<String> deleteAccount(@PathVariable String userEmail);
}
