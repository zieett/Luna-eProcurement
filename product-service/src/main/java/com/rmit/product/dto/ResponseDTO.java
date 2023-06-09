package com.rmit.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTO<T> {
    private String message;
    private T data;

    public ResponseDTO(String message) {
        this.message = message;
    }

    public ResponseDTO(T data) {
        this.data = data;
    }

}
