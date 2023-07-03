package com.rmit.product.repository;

import com.rmit.product.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(String code);

    List<Product> findByLegalEntityCode(String legalEntityCode);

    Optional<Product> findByCodeAndLegalEntityCode(String code, String productCode);
}
