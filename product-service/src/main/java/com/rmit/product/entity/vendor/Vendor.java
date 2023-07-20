package com.rmit.product.entity.vendor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vendor")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String businessName;
    @NotNull
    private String businessNumber;
    @NotNull
    private String code;
    private String totalPurchase;
    private String amountPayable;
    private String mediaFile;
    private String notes;

    @Enumerated(EnumType.STRING)
    private Groups groups;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private List<Contact> contacts;
    ;
}
