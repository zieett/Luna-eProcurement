package com.rmit.product.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {

    //    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private String timestamp;
    private String message;
    private String detail;

}
