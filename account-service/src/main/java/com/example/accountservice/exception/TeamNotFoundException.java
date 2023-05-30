package com.example.accountservice.exception;

public class TeamNotFoundException extends RuntimeException{
    public TeamNotFoundException(String message){
        super(message);
    }
}
