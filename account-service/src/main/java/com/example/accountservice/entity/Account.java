package com.example.accountservice.entity;

import com.example.accountservice.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String email;
    private String username;
//    private String phoneNumber;
//    private String gender;
    @Enumerated(EnumType.STRING)
    private Roles role;
    private String legalEntityCode;
}
