package com.rmit.product.exception;

import lombok.RequiredArgsConstructor;
import org.hibernate.JDBCException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomResponseEntityException extends ResponseEntityExceptionHandler {

    private final DateTimeFormatter dateTimeFormatter;

    @ExceptionHandler({ProductNotFoundException.class, VendorNotFoundException.class, LegalEntityNotFoundException.class})
    public final ResponseEntity<Object> notFoundException(Exception ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(LocalDateTime.now().format(dateTimeFormatter), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({JDBCException.class})
    public final ResponseEntity<Object> JdbcException(Exception ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(LocalDateTime.now().format(dateTimeFormatter), ex.getCause().getCause().getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        ex.getBindingResult().getAllErrors().forEach((error) ->{
//
//            String fieldName = ((FieldError) error).getField();
//            String message = error.getDefaultMessage();
//            errors.put(fieldName, message);
//        });
        ErrorDetail errorDetail = new ErrorDetail(LocalDateTime.now().format(dateTimeFormatter),
            ex.getFieldError().getDefaultMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }
}
