package com.rmit.product.controllers;

import com.rmit.product.dto.ProductDTO;
import com.rmit.product.entity.Product;
import com.rmit.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
//    private ProductRepository productRepository;
    @GetMapping("/product")
    public ProductDTO getProduct(){return new ProductDTO("Product1","123$");}

//    @GetMapping("/product")
//    public Product createProduct(@RequestBody Product product){
//        return productRepository.save(product);
//    }
}
