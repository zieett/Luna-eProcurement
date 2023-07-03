package com.rmit.product.feignclients;

import com.rmit.product.dto.AccountDTO;
import com.rmit.product.dto.LegalEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "account-service/api")
public interface AccountFeignClient {

    @GetMapping("/account/{email}")
    AccountDTO getAccountByEmail(@PathVariable String email);

    @GetMapping(value = "/entity/{entityCode}")
    LegalEntity getLegalEntityByCode(@PathVariable String entityCode);
}
