package com.rmit.product.controllers;

import com.rmit.product.dto.ProductDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @GetMapping("/product")
    public ProductDTO getProduct(){return new ProductDTO("Product1","123$");}
}
