package com.rmit.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactDTO {
    @NotNull(message = "Name must not be null")
    private String name;
    @NotNull(message = "Phone must not be null")
    private String phone;
    @NotNull(message = "Position must not be null")
    private String position;
}
