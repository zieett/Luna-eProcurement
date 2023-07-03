package com.rmit.product.service.impl;

import com.rmit.product.dto.LegalEntity;
import com.rmit.product.dto.ProductDTO;
import com.rmit.product.dto.ResponseDTO;
import com.rmit.product.entity.product.Product;
import com.rmit.product.exception.LegalEntityNotFoundException;
import com.rmit.product.exception.ProductNotFoundException;
import com.rmit.product.feignclients.AccountFeignClient;
import com.rmit.product.repository.ProductRepository;
import com.rmit.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final AccountFeignClient accountFeignClient;

    @Override
    public ResponseEntity<ResponseDTO<ProductDTO>> createProduct(ProductDTO productDTO) {
        LegalEntity legalEntity = accountFeignClient.getLegalEntityByCode(productDTO.getLegalEntityCode());
        if (ObjectUtils.isEmpty(legalEntity)) {
            throw new LegalEntityNotFoundException("Cannot find legal entity with code: " + productDTO.getLegalEntityCode());
        }
        if (productRepository.findByCodeAndLegalEntityCode(productDTO.getCode(), legalEntity.getCode()).isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseDTO<>("This product is already exist"));
        }
        Product product = modelMapper.map(productDTO, Product.class);
        productRepository.save(product);
        return ResponseEntity.ok(new ResponseDTO<>("Product added", productDTO));
    }

    @Override
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @Override
    public ResponseEntity<String> deleteProduct(String productCode) {
        Product product = productRepository.findByCode(productCode).orElseThrow(() -> new ProductNotFoundException("Cannot find product with code: " + productCode));
        productRepository.delete(product);
        return ResponseEntity.ok("Product deleted");
    }

    @Override
    public ResponseEntity<ResponseDTO<List<ProductDTO>>> getProductsInLegalEntity(String legalEntityCode) {
        LegalEntity legalEntity = accountFeignClient.getLegalEntityByCode(legalEntityCode);
        if (ObjectUtils.isEmpty(legalEntity)) {
            throw new LegalEntityNotFoundException("Cannot find legal entity with code: " + legalEntityCode);
        }
        List<Product> products = productRepository.findByLegalEntityCode(legalEntity.getCode());
        List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        return ResponseEntity.ok(new ResponseDTO<>(productDTOS));
    }

}
