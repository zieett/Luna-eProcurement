package com.rmit.product.service.impl;

import com.rmit.product.dto.PageResponse;
import com.rmit.product.dto.ResponseDTO;
import com.rmit.product.dto.VendorDTO;
import com.rmit.product.entity.vendor.Vendor;
import com.rmit.product.repository.VendorRepository;
import com.rmit.product.service.VendorService;
import com.rmit.product.ultils.Utils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

    @Override
    public ResponseEntity<PageResponse<List<VendorDTO>>> getVendorsPageable(int page, int size, String sortBy, String sortDirection, String search, String searchBy) {
        Sort sort;
        Pageable pageable;
        Page<Vendor> vendors;
        if (!StringUtils.isBlank(sortBy)) sort = Sort.by(Utils.getSortDirection(sortDirection), sortBy);
        else sort = null;
        if (!ObjectUtils.isEmpty(sort)) pageable = PageRequest.of(page, size, sort);
        else pageable = PageRequest.of(page, size, Utils.getSortDirection(sortDirection), "code");
        if (!StringUtils.isBlank(search)) vendors = getSearchVendor(search, searchBy, pageable);
        else vendors = vendorRepository.findAll(pageable);
        Page<VendorDTO> vendorDTOS = vendors.map(vendor -> modelMapper.map(vendor, VendorDTO.class));
        return ResponseEntity.ok(new PageResponse<>(vendorDTOS.getContent(), vendorDTOS.getPageable().getPageNumber() + 1, vendorDTOS.getSize(), vendorDTOS.getTotalPages(), vendorDTOS.getTotalElements()));

    }

    public Page<Vendor> getSearchVendor(String search, String searchBy, Pageable pageable) {
        return switch (searchBy) {
            case "code" -> vendorRepository.searchVendorByCode(search, pageable);
            case "name" -> vendorRepository.searchVendorByBusinessName(search, pageable);
            case "businessNumber" -> vendorRepository.searchVendorByBusinessNumber(search, pageable);
            default -> vendorRepository.findAll(pageable);
        };
    }
}
