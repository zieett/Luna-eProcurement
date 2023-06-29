package com.example.accountservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "legal_entity")
public class LegalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "Legal Entity name must not be null")
    @Column(name = "name", updatable = false)
    private String name;
    @NotNull(message = "Legal Entity code must not be null")
    @Column(name = "code", updatable = false)
    private String code;
}
