package com.example.accountservice.exception;

public class LegalEntityNotFoundExeption extends RuntimeException {
    public LegalEntityNotFoundExeption(String message) {
        super(message);
    }
}
