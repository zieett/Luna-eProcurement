package com.rmit.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDTO<T> {
    private String message;
    private int status;
    private List<T> data;

    public ResponseDTO(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public ResponseDTO(int status, List<T> data) {
        this.status = status;
        this.data = data;
    }

}
