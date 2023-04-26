package com.example.accountservice.feignclients;

import com.example.accountservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "api-gateway")
public interface ProductFeignClient {
    @GetMapping("product-service/product")
    ProductDTO getProduct();
}
