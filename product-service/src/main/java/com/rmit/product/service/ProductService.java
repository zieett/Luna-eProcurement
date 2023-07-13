package com.rmit.product.service;

import com.rmit.product.dto.PageResponse;
import com.rmit.product.dto.ProductDTO;
import com.rmit.product.dto.ProvidedVendorCode;
import com.rmit.product.dto.ResponseDTO;
import com.rmit.product.entity.product.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<ResponseDTO<ProductDTO>> createProduct(ProductDTO productDTO);

    ResponseEntity<List<Product>> getAllProducts();

    ResponseEntity<String> deleteProduct(String legalEntityCode, String productCode);

    ResponseEntity<ResponseDTO<List<ProductDTO>>> getProductsInLegalEntity(String legalEntityCode);

    ResponseEntity<PageResponse<List<ProductDTO>>> getProductsInLegalEntityPageable(String legalEntityCode, int page, int size, String sortBy, String sortDirection, String search);

    ResponseEntity<String> assignProductToVedorByCode(ProvidedVendorCode providedVendorCode);
}
