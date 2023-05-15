package com.rmit.authservice.exception;

import com.rmit.authservice.dto.ResponseDTO;
import java.time.LocalDateTime;
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ResponseDTO errorDetail = new ResponseDTO(LocalDateTime.now(), ex.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({InvalidTokenException.class})
    public final ResponseEntity<Object> invalidTokenException(Exception ex, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(LocalDateTime.now(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity(errorDetail, HttpStatus.UNAUTHORIZED);
    }

}
