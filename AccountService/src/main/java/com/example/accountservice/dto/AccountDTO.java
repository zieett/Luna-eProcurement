package com.example.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private String name;
    private String age;
    private ProductDTO productDTO;

}
