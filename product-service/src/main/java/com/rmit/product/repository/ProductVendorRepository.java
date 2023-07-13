package com.rmit.product.repository;

import com.rmit.product.entity.ProductVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductVendorRepository extends JpaRepository<ProductVendor, Long> {
    Optional<ProductVendor> findByProductCodeAndVendorCode(String productCode, String vendorCode);
}
