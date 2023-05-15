package com.example.accountservice.exception;

import java.time.LocalDateTime;
import org.apache.http.conn.EofSensorInputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler({AccountNotFoundException.class,LegalEntityNotFoundExeption.class})
    public final ResponseEntity<Object> accountNotFoundException(Exception ex, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity(errorDetail, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(LocalDateTime.now(), ex.getFieldError().getDefaultMessage(),request.getDescription(false));
        return new ResponseEntity(errorDetail, HttpStatus.BAD_REQUEST);
    }
}
