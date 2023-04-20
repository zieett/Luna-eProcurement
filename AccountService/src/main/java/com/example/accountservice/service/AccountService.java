package com.example.accountservice.service;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.dto.ProductDTO;
import com.example.accountservice.feignclients.ProductFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final ProductFeignClient productFeignClient;
    public AccountDTO getAccount(){
//        ProductDTO productDTO = new RestTemplate().getForObject("http://localhost:8100/product",ProductDTO.class);
        return new AccountDTO("Viet","23",productFeignClient.getProduct());
    }
}
