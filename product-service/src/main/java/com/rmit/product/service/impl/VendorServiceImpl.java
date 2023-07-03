package com.rmit.product.service.impl;

import com.rmit.product.dto.ResponseDTO;
import com.rmit.product.dto.VendorDTO;
import com.rmit.product.entity.vendor.Vendor;
import com.rmit.product.repository.VendorRepository;
import com.rmit.product.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<ResponseDTO<VendorDTO>> createVendor(VendorDTO vendorDTO) {
        Optional<Vendor> vendor1 = vendorRepository.findByCode(vendorDTO.getCode());
        if (vendor1.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseDTO<>("Vendor is already exist"));
        }
        Vendor vendor = modelMapper.map(vendorDTO, Vendor.class);
        vendorRepository.save(vendor);
        return ResponseEntity.ok(new ResponseDTO<>("Vendor added", vendorDTO));
    }

    @Override
    public ResponseEntity<List<Vendor>> getVendors() {
        return ResponseEntity.ok(vendorRepository.findAll());
    }
}
