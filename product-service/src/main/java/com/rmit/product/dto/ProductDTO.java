package com.rmit.product.dto;

import com.rmit.product.entity.product.Dimension;
import com.rmit.product.entity.product.MediaFile;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
