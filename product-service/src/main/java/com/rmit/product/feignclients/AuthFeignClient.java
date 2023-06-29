package com.rmit.product.feignclients;

import com.rmit.product.dto.AuthDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "auth-service/auth")
public interface AuthFeignClient {
    @PostMapping("/get-auth")
    ResponseEntity<AuthDTO> getAuth(@RequestBody String userEmail);
}
