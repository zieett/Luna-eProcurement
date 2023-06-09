package com.example.accountservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "department")
public class Department {
    @Id
    @NotNull(message = "Department code must not be null")
    @Column(name = "code", updatable = false)
    private String code;
    @NotNull(message = "Department name must not be null")
    @Column(name = "name")
    private String name;
    @NotNull(message = "Department must belong to one entity")
    private String legalEntityCode;
}
