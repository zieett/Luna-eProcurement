package com.example.accountservice.feignclients;

import com.example.accountservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "product-service/api")
public interface ProductFeignClient {
    @GetMapping("product")
    ProductDTO getProduct();
}
