package com.rmit.product.service;

import com.rmit.product.entity.vendor.Vendor;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface VendorService {

    ResponseEntity<Vendor> createVendor(Vendor vendor);

    ResponseEntity<List<Vendor>> getVendors();
}
