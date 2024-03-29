package com.rmit.product.service;

import com.rmit.product.dto.ContactDTO;
import com.rmit.product.dto.PageResponse;
import com.rmit.product.dto.ResponseDTO;
import com.rmit.product.dto.VendorDTO;
import com.rmit.product.entity.vendor.Vendor;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VendorService {

    ResponseEntity<ResponseDTO<VendorDTO>> createVendor(VendorDTO vendorDTO);

    ResponseEntity<List<Vendor>> getVendors();

    ResponseEntity<PageResponse<List<VendorDTO>>> getVendorsPageable(int page, int size, String sortBy, String sortDirection, String search);

    ResponseEntity<String> deleteVendor(String vendorCode);

    ResponseEntity<String> updateVendor(String vendorCode, VendorDTO vendorDTO);

    ResponseEntity<VendorDTO> getVendor(String vendorCode);

    ResponseEntity<String> addContact(String vendorCode, List<ContactDTO> contactDTOS);
}
