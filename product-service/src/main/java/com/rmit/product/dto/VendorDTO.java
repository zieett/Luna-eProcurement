package com.rmit.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rmit.product.entity.vendor.Address;
import com.rmit.product.entity.vendor.Contact;
import com.rmit.product.entity.vendor.Groups;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendorDTO {
    @NotNull(message = "businessName must not be null")
    private String businessName;
    @NotNull(message = "businessNumber must not be null")
    private String businessNumber;
    @NotNull(message = "Code must not be null")
    private String code;
    private String totalPurchase;
    private String amountPayable;
    private String mediaFile;
    private String notes;
    private Groups groups;
    private Address address;
    private Contact contact;
}
