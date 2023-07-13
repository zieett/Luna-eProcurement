package com.rmit.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rmit.product.entity.product.Dimension;
import com.rmit.product.entity.product.MediaFile;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ProductDTO implements Serializable {
    private String name;
    private String description;
    private String brand;
    @NotNull(message = "Code must not be null")
    private String code;
    private String category;
    private String weight;
    private Dimension dimension;
    private String color;
    private String material;
    private MediaFile mediaFile;
    @NotNull(message = "Legal Entity Code must not be null")
    private String legalEntityCode;
    @JsonIgnore
    private List<ProvidedVendor> newVendors;
    @JsonIgnore
    private List<ProvidedVendorCode> vendorCodes;
    private List<ProvidedVendorInfoDTO> providedVendorInfo;
}
