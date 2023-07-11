package com.rmit.product.controllers;

import com.rmit.product.dto.PageResponse;
import com.rmit.product.dto.ResponseDTO;
import com.rmit.product.dto.VendorDTO;
import com.rmit.product.service.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @PostMapping("/vendor")
    public ResponseEntity<ResponseDTO<VendorDTO>> createVendor(@Valid @RequestBody VendorDTO vendorDTO) {
        return vendorService.createVendor(vendorDTO);
    }

    //    @GetMapping("/vendor")
//    @Auth(role = Roles.MANAGER)
//    public ResponseEntity<List<Vendor>> getVendors(@RequestHeader String userInfo) {
//        return vendorService.getVendors();
//    }
    @GetMapping("/vendor")
    public ResponseEntity<PageResponse<List<VendorDTO>>> getVendorPageable(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "businessName") String searchBy,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return vendorService.getVendorsPageable(page - 1, size, sortBy, sortDirection, search, searchBy);
    }
}
