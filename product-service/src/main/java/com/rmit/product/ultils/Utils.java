package com.rmit.product.ultils;

import org.springframework.data.domain.Sort;

public class Utils {
    public static Sort.Direction getSortDirection(String sortDirection) {
        if (sortDirection.equals("asc")) return Sort.Direction.ASC;
        return Sort.Direction.DESC;
    }
}
