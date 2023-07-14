package com.rmit.product.repository;

import com.rmit.product.dto.ProvidedVendorInfoDTO;
import com.rmit.product.entity.ProductVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVendorRepository extends JpaRepository<ProductVendor, Long> {
    Optional<ProductVendor> findByProductCodeAndVendorCode(String productCode, String vendorCode);

    List<ProductVendor> findByVendorCode(String vendorCode);

    List<ProductVendor> findByProductCode(String productCode);

    @Query("SELECT new com.rmit.product.dto.ProvidedVendorInfoDTO(v.code,v.businessName,pv.price) FROM Vendor v ,ProductVendor pv WHERE " +
            "pv.productCode = :productCode " +
            "and pv.vendorCode = v.code"
    )
    List<ProvidedVendorInfoDTO> getVendorNamesByProductCode(String productCode);
}
