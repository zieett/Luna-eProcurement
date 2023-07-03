package com.rmit.product.controllers;

import com.rmit.product.aspect.Auth;
import com.rmit.product.dto.ResponseDTO;
import com.rmit.product.dto.VendorDTO;
import com.rmit.product.entity.vendor.Vendor;
import com.rmit.product.enums.Roles;
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

    @GetMapping("/vendor")
    @Auth(role = Roles.MANAGER)
    public ResponseEntity<List<Vendor>> getVendors(@RequestHeader String userInfo) {
        return vendorService.getVendors();
    }
}
