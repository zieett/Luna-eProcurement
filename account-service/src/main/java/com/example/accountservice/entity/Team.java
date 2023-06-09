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
@Table(name = "team")
public class Team {
    @Id
    @NotNull(message = "Department code must not be null")
    @Column(name = "code", updatable = false)
    private String code;
    @NotNull(message = "Team name must not be null")
    @Column(name = "name")
    private String name;
    @NotNull(message = "Team must belong to one department")
    private String departmentCode;
}
