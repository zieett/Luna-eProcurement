package com.rmit.product.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_dimension")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dimension {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private String width;
    private String height;
    private String length;
    @OneToOne(mappedBy = "dimension")
    @JsonIgnore
    private Product product;
}
