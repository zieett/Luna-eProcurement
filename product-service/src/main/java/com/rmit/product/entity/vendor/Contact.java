package com.rmit.product.entity.vendor;

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
@Table(name = "vendor_contact",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"name", "vendor_id"})
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private String name;
    private String phone;
    private String position;
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    @JsonIgnore
    private Vendor vendor;

}
