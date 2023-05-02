package com.rmit.authservice.feignclients;

import com.rmit.authservice.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "account-service/api")
public interface AccountFeignClient {
    @PostMapping(value = "/account",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.ALL_VALUE})
    ResponseEntity<String> createAccount(@RequestBody AccountDTO accountDTO);
}
