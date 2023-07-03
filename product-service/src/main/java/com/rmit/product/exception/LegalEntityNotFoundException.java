package com.rmit.product.exception;

public class LegalEntityNotFoundException extends RuntimeException {
    public LegalEntityNotFoundException(String message) {
        super(message);
    }
}
