package com.example.accountservice.exception;

public class LegalEntityNotFoundException extends RuntimeException {
    public LegalEntityNotFoundException(String message) {
        super(message);
    }
}
