package com.rmit.product.repository;

import com.rmit.product.entity.vendor.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByNameAndVendor_Code(String name, String code);
}
