package com.rmit.authservice.entity;

import com.rmit.authservice.enums.Permission;
import com.rmit.authservice.enums.Roles;
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
public class UserCredential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String username;
    @Enumerated(EnumType.STRING)
    private Roles role;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Permission> permissions;
}
