package com.example.accountservice.entity;


import com.example.accountservice.enums.Roles;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_legalEntity")
public class AccountLegalEntity {

    @Id
    private Long id;
    private Long accountId;
    private Long legalEntityId;
    @Enumerated(EnumType.STRING)
    private Roles role;
}
