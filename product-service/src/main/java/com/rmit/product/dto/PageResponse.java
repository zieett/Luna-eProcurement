package com.rmit.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResponse<T> {
    private T data;
    private int currentPage;
    private int size;
    private int totalPages;
    private long totalElements;

}
