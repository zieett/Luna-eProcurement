package com.rmit.product.entity.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String brand;
    @NotNull(message = "Code must not be null")
    private String code;
    private String sku;
    private String category;
    private String weight;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dimension_id", referencedColumnName = "id")
    private Dimension dimension;
    private String color;
    private String material;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "media_file_id", referencedColumnName = "id")
    private MediaFile mediaFile;
    private String legalEntityCode;
}
