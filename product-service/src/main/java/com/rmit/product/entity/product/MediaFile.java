package com.rmit.product.entity.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_media_file")
public class MediaFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String productImage;
    private String videoLink;
}
