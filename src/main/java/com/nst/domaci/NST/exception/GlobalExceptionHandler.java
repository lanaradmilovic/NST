package com.nst.domaci.NST.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    private ResponseEntity<MyErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex) {
        MyErrorDetails myErrorDetails = new MyErrorDetails(ex.getMessage());
        return new ResponseEntity<>(myErrorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EntityAlreadyExistsException.class)
    private ResponseEntity<MyErrorDetails> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex){
        MyErrorDetails myErrorDetails = new MyErrorDetails(ex.getMessage());
        return  new ResponseEntity<>(myErrorDetails, HttpStatus.BAD_REQUEST);
    }
}
