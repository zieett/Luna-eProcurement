package com.rmit.product.service.impl;

import com.rmit.product.entity.vendor.Vendor;
import com.rmit.product.repository.VendorRepository;
import com.rmit.product.service.VendorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    @Override
    public ResponseEntity<Vendor> createVendor(Vendor vendor) {
        vendorRepository.save(vendor);
        return ResponseEntity.ok(vendor);
    }

    @Override
    public ResponseEntity<List<Vendor>> getVendors() {
        return ResponseEntity.ok(vendorRepository.findAll());
    }
}
