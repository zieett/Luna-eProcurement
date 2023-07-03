package com.rmit.product.controllers;

import com.rmit.product.dto.ProductDTO;
import com.rmit.product.dto.ResponseDTO;
import com.rmit.product.entity.product.Product;
import com.rmit.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProduct() {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{legalEntityCode}")
    public ResponseEntity<ResponseDTO<List<ProductDTO>>> getProductInLegalEntity(@PathVariable String legalEntityCode) {
        return productService.getProductsInLegalEntity(legalEntityCode);
    }

    @PostMapping("/product")
    public ResponseEntity<ResponseDTO<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @DeleteMapping("/product/{productCode}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productCode) {
        return productService.deleteProduct(productCode);
    }
}
