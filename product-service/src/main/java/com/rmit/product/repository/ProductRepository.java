package com.rmit.product.repository;

import com.rmit.product.dto.ProductDTO;
import com.rmit.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

}
