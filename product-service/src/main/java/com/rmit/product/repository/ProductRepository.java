package com.rmit.product.repository;

import com.rmit.product.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(String code);

    List<Product> findByLegalEntityCode(String legalEntityCode);

    Page<Product> findAllByLegalEntityCode(String legalEntityCode, Pageable pageable);

    Optional<Product> findByCodeAndLegalEntityCode(String code, String productCode);

    @Query("SELECT p FROM Product p WHERE " +
            "p.code LIKE CONCAT('%',:search, '%')")
    Page<Product> searchProductByCode(String search, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "p.name LIKE CONCAT('%',:search, '%')")
    Page<Product> searchProductByName(String search, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(p.code LIKE CONCAT('%',:search, '%')" +
            "or p.name LIKE CONCAT('%', :search, '%')" +
            "or p.sku LIKE CONCAT('%', :search, '%'))" +
            "and p.legalEntityCode = :legalEntityCode"
    )
    Page<Product> searchProductByCodeOrNameOrSku(String search, String legalEntityCode, Pageable pageable);
}
