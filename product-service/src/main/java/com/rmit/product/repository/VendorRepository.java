package com.rmit.product.repository;

import com.rmit.product.entity.vendor.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Optional<Vendor> findByCode(String code);

    @Query("SELECT v FROM Vendor v WHERE " +
            "v.code LIKE CONCAT('%',:search, '%')")
    Page<Vendor> searchVendorByCode(String search, Pageable pageable);

    @Query("SELECT v FROM Vendor v WHERE " +
            "v.businessName LIKE CONCAT('%',:search, '%')")
    Page<Vendor> searchVendorByBusinessName(String search, Pageable pageable);

    @Query("SELECT v FROM Vendor v WHERE " +
            "v.businessNumber LIKE CONCAT('%',:search, '%')")
    Page<Vendor> searchVendorByBusinessNumber(String search, Pageable pageable);
    @Query("SELECT v FROM Vendor v WHERE " +
            "v.code LIKE CONCAT('%',:search, '%')" +
            "Or v.businessName LIKE CONCAT('%', :search, '%')" +
            "Or v.businessNumber LIKE CONCAT('%', :search, '%')"
    )
    Page<Vendor> searchVendorByCodeOrBusinessNameOrBusinessNumber(String search, Pageable pageable);
}
