package com.rmit.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "product_vendor")
@Builder
public class ProductVendor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String productCode;
    private String vendorCode;
    private String price;

    public ProductVendor(String productCode, String vendorCode, String price) {
        this.productCode = productCode;
        this.vendorCode = vendorCode;
        this.price = price;
    }


}
