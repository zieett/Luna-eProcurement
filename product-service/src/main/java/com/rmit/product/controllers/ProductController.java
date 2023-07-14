package com.rmit.product.controllers;

import com.rmit.product.dto.AssignVendorDTO;
import com.rmit.product.dto.PageResponse;
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

    //    @GetMapping("/product/{legalEntityCode}")
//    public ResponseEntity<ResponseDTO<List<ProductDTO>>> getProductInLegalEntity(@PathVariable String legalEntityCode) {
//        return productService.getProductsInLegalEntity(legalEntityCode);
//    }
    @GetMapping("/product/{legalEntityCode}")
    public ResponseEntity<PageResponse<List<ProductDTO>>> getProductInLegalEntityPageable(@PathVariable String legalEntityCode,
                                                                                          @RequestParam(required = false) String search,
                                                                                          @RequestParam(defaultValue = "1") int page,
                                                                                          @RequestParam(defaultValue = "150") int size,
                                                                                          @RequestParam(required = false) String sortBy,
                                                                                          @RequestParam(defaultValue = "asc") String sortDirection) {
        return productService.getProductsInLegalEntityPageable(legalEntityCode, page - 1, size, sortBy, sortDirection, search);
    }

    @GetMapping("/product/{legalEntityCode}/{productCode}")
    public ResponseEntity<ProductDTO> getProductDetail(@PathVariable String legalEntityCode, @PathVariable String productCode) {
        return productService.getProductDetail(legalEntityCode, productCode);
    }

    @PostMapping("/product")
    public ResponseEntity<ResponseDTO<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @PostMapping("/product/assignToVendor")
    public ResponseEntity<String> assignProductToVendor(@Valid @RequestBody AssignVendorDTO assignVendorDTO) {
        return productService.assignProductToVendorByCode(assignVendorDTO);
    }

    @DeleteMapping("/product/{legalEntityCode}/{productCode}")
    public ResponseEntity<String> deleteProduct(@PathVariable String legalEntityCode, @PathVariable String productCode) {
        return productService.deleteProduct(legalEntityCode, productCode);
    }

    @PatchMapping("/product/{legalEntityCode}/{productCode}")
    public ResponseEntity<String> updateProduct(@PathVariable String legalEntityCode, @PathVariable String productCode, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(legalEntityCode, productCode, productDTO);
    }

    @PatchMapping("/product/{legalEntityCode}/{productCode}/{vendorCode}/{price}")
    public ResponseEntity<String> updateProductPrice(@PathVariable String legalEntityCode, @PathVariable String productCode, @PathVariable String vendorCode, @PathVariable String price) {
        return productService.updateProductPrice(legalEntityCode, productCode, vendorCode, price);
    }
}
