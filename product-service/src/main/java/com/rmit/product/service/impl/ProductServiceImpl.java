package com.rmit.product.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmit.product.dto.*;
import com.rmit.product.entity.ProductVendor;
import com.rmit.product.entity.product.Product;
import com.rmit.product.entity.vendor.Vendor;
import com.rmit.product.exception.LegalEntityNotFoundException;
import com.rmit.product.exception.ProductNotFoundException;
import com.rmit.product.exception.VendorNotFoundException;
import com.rmit.product.feignclients.AccountFeignClient;
import com.rmit.product.repository.ProductRepository;
import com.rmit.product.repository.ProductVendorRepository;
import com.rmit.product.repository.VendorRepository;
import com.rmit.product.service.ProductService;
import com.rmit.product.ultils.Utils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final AccountFeignClient accountFeignClient;
    private final VendorRepository vendorRepository;
    private final ProductVendorRepository productVendorRepository;
    private final ObjectMapper objectMapper;


    @Override
    public ResponseEntity<ResponseDTO<ProductDTO>> createProduct(ProductDTO productDTO) {
        LegalEntity legalEntity = accountFeignClient.getLegalEntityByCode(productDTO.getLegalEntityCode());
        if (ObjectUtils.isEmpty(legalEntity)) {
            throw new LegalEntityNotFoundException("Cannot find legal entity with code: " + productDTO.getLegalEntityCode());
        }
        if (productRepository.findByCodeAndLegalEntityCode(productDTO.getCode(), legalEntity.getCode()).isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseDTO<>("This product is already exist"));
        }
        if (!CollectionUtils.isEmpty(productDTO.getVendorCodes()) && !CollectionUtils.isEmpty(productDTO.getNewVendors()))
            return ResponseEntity.ok(new ResponseDTO<>("Conflict vendor assign", productDTO));
        Product product = modelMapper.map(productDTO, Product.class);
        if (!CollectionUtils.isEmpty(productDTO.getVendorCodes())) {
            productDTO.getVendorCodes().forEach(providedVendorCode -> {
                vendorRepository.findByCode(providedVendorCode.getVendorCode()).orElseThrow(() -> new VendorNotFoundException("Cannot find vendor with code: " + providedVendorCode.getVendorCode()));
                ProductVendor productVendor = new ProductVendor(productDTO.getCode(), providedVendorCode.getVendorCode(), providedVendorCode.getPrice());
                productVendorRepository.save(productVendor);
            });
            productRepository.save(product);
            return ResponseEntity.ok(new ResponseDTO<>("Product added", productDTO));
        }
        if (!CollectionUtils.isEmpty(productDTO.getNewVendors())) {
            productDTO.getNewVendors().forEach(providedVendor -> {
                Optional<Vendor> vendor = vendorRepository.findByCode(providedVendor.getVendor().getCode());
                if (vendor.isPresent())
                    throw new RuntimeException("This vendor is already exist: " + providedVendor.getVendor().getCode());
                ProductVendor productVendor = new ProductVendor(productDTO.getCode(), providedVendor.getVendor().getCode(), providedVendor.getPrice());
                vendorRepository.save(modelMapper.map(providedVendor.getVendor(), Vendor.class));
                productVendorRepository.save(productVendor);
            });
            productRepository.save(product);
            return ResponseEntity.ok(new ResponseDTO<>("Product added", productDTO));
        }
        productRepository.save(product);
        return ResponseEntity.ok(new ResponseDTO<>("Product added", productDTO));
    }

    @Override
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @Override
    public ResponseEntity<String> deleteProduct(String legalEntityCode, String productCode) {
        Product product = productRepository.findByCodeAndLegalEntityCode(productCode, legalEntityCode).orElseThrow(() -> new ProductNotFoundException("Cannot find product with code: " + productCode));
        if (ObjectUtils.isEmpty(product)) return ResponseEntity.ok("Cannot find any product to delete");
        List<ProductVendor> productVendor = productVendorRepository.findByProductCode(productCode);
        productVendorRepository.deleteAll(productVendor);
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

    @Override
    public ResponseEntity<PageResponse<List<ProductDTO>>> getProductsInLegalEntityPageable(String legalEntityCode, int page, int size, String sortBy, String sortDirection, String search) {
        LegalEntity legalEntity = accountFeignClient.getLegalEntityByCode(legalEntityCode);
        if (ObjectUtils.isEmpty(legalEntity)) {
            throw new LegalEntityNotFoundException("Cannot find legal entity with code: " + legalEntityCode);
        }
        Sort sort;
        Pageable pageable;
        Page<Product> products;
        if (!StringUtils.isBlank(sortBy)) sort = Sort.by(Utils.getSortDirection(sortDirection), sortBy);
        else sort = null;
        if (!ObjectUtils.isEmpty(sort)) pageable = PageRequest.of(page, size, sort);
        else pageable = PageRequest.of(page, size);
        if (!StringUtils.isBlank(search))
            products = productRepository.searchProductByCodeOrNameOrSku(search, legalEntityCode, pageable);
        else products = productRepository.findAllByLegalEntityCode(legalEntityCode, pageable);
        Page<ProductDTO> productDTOS = products.map(product -> modelMapper.map(product, ProductDTO.class));
        productDTOS.stream().forEach(productDTO -> productDTO.setProvidedVendorInfo(productVendorRepository.getVendorNamesByProductCode(productDTO.getCode())));
        return ResponseEntity.ok(new PageResponse<>(productDTOS.getContent(), productDTOS.getPageable().getPageNumber() + 1, productDTOS.getSize(), productDTOS.getTotalPages(), productDTOS.getTotalElements()));
    }

    @Override
    public ResponseEntity<String> assignProductToVendorByCode(AssignVendorDTO assignVendorDTO) {
        if (productVendorRepository.findByProductCodeAndVendorCode(assignVendorDTO.getProductCode(), assignVendorDTO.getVendorCode()).isPresent())
            return ResponseEntity.badRequest().body("This " + assignVendorDTO.getProductCode() + " product has been add to " + assignVendorDTO.getVendorCode() + " vendor");
        vendorRepository.findByCode(assignVendorDTO.getVendorCode()).orElseThrow(() -> new VendorNotFoundException("Cannot find vendor with code: " + assignVendorDTO.getVendorCode()));
        ProductVendor productVendor = new ProductVendor(assignVendorDTO.getProductCode(), assignVendorDTO.getVendorCode(), assignVendorDTO.getPrice());
        productVendorRepository.save(productVendor);
        return ResponseEntity.ok("Assign product successfully");
    }

    @Override
    public ResponseEntity<ProductDTO> getProductDetail(String legalEntityCode, String productCode) {
        LegalEntity legalEntity = accountFeignClient.getLegalEntityByCode(legalEntityCode);
        if (ObjectUtils.isEmpty(legalEntity)) {
            throw new LegalEntityNotFoundException("Cannot find legal entity with code: " + legalEntityCode);
        }
        Product product = productRepository.findByCodeAndLegalEntityCode(productCode, legalEntityCode).orElseThrow(() -> new ProductNotFoundException("Cannot find product " + productCode + " in legal entity: " + legalEntityCode));
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setProvidedVendorInfo(productVendorRepository.getVendorNamesByProductCode(productCode));
        return ResponseEntity.ok(productDTO);
    }

    @Override
    public ResponseEntity<String> updateProduct(String legalEntityCode, String productCode, ProductDTO productDTO) {
        LegalEntity legalEntity = accountFeignClient.getLegalEntityByCode(legalEntityCode);
        if (ObjectUtils.isEmpty(legalEntity)) {
            throw new LegalEntityNotFoundException("Cannot find legal entity with code: " + legalEntityCode);
        }
        Product product = productRepository.findByCodeAndLegalEntityCode(productCode, legalEntityCode).orElseThrow(() -> new ProductNotFoundException("Cannot find product " + productCode + " in legal entity: " + legalEntityCode));
        try {
            productRepository.save(objectMapper.updateValue(product, productDTO));
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Product updated");
    }

    @Override
    public ResponseEntity<String> updateProductPrice(String legalEntityCode, String productCode, String vendorCode, String price) {
        LegalEntity legalEntity = accountFeignClient.getLegalEntityByCode(legalEntityCode);
        if (ObjectUtils.isEmpty(legalEntity)) {
            throw new LegalEntityNotFoundException("Cannot find legal entity with code: " + legalEntityCode);
        }
        Product product = productRepository.findByCodeAndLegalEntityCode(productCode, legalEntityCode).orElseThrow(() -> new ProductNotFoundException("Cannot find product " + productCode + " in legal entity: " + legalEntityCode));
        Vendor vendor = vendorRepository.findByCode(vendorCode).orElseThrow(() -> new VendorNotFoundException("Cannot find vendor with code: " + vendorCode));
        ProductVendor productVendor = productVendorRepository.findByProductCodeAndVendorCode(product.getCode(), vendor.getCode()).orElseThrow(() -> new VendorNotFoundException("This product is not assign to this vendor"));
        productVendor.setPrice(price);
        productVendorRepository.save(productVendor);
        return ResponseEntity.ok("Product's price updated");
    }
}
