package com.rmit.product.controllers;

import com.rmit.product.aspect.Auth;
import com.rmit.product.aspect.Role;
import com.rmit.product.entity.vendor.Vendor;
import com.rmit.product.enums.Permission;
import com.rmit.product.enums.Roles;
import com.rmit.product.service.VendorService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @PostMapping("/vendor")
    public ResponseEntity<Vendor> createVendor(@Valid @RequestBody Vendor vendor) {
        return vendorService.createVendor(vendor);
    }

    @GetMapping("/vendor")
    @Auth(value = @Role(role = Roles.MANAGER, permissions = {Permission.CREATE}))
    public ResponseEntity<List<Vendor>> getVendors(@RequestHeader String userInfo) {
        return vendorService.getVendors();
    }
}
