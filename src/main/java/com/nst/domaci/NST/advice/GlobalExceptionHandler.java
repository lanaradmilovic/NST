package com.nst.domaci.NST.advice;

import com.nst.domaci.NST.exception.*;
import com.nst.domaci.NST.exception.IllegalArgumentException;
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
    @ExceptionHandler(DepartmentMismatchException.class)
    private ResponseEntity<MyErrorDetails> handleDepartmentMismatchException (DepartmentMismatchException ex){
        MyErrorDetails myErrorDetails = new MyErrorDetails(ex.getMessage());
        return  new ResponseEntity<>(myErrorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MemberNotInDepartmentException.class)
    private ResponseEntity<MyErrorDetails> handleMemberNotInDepartmentException (MemberNotInDepartmentException ex){
        MyErrorDetails myErrorDetails = new MyErrorDetails(ex.getMessage());
        return  new ResponseEntity<>(myErrorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RoleConflictException.class)
    private ResponseEntity<MyErrorDetails> handleRoleConflictException (RoleConflictException ex){
        MyErrorDetails myErrorDetails = new MyErrorDetails(ex.getMessage());
        return  new ResponseEntity<>(myErrorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(com.nst.domaci.NST.exception.IllegalArgumentException.class)
    private ResponseEntity<MyErrorDetails> handleIllegalArgumentException (IllegalArgumentException ex){
        MyErrorDetails myErrorDetails = new MyErrorDetails(ex.getMessage());
        return  new ResponseEntity<>(myErrorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(com.nst.domaci.NST.exception.SubjectMismatchException.class)
    private ResponseEntity<MyErrorDetails> handleSubjectMismatchException (SubjectMismatchException ex){
        MyErrorDetails myErrorDetails = new MyErrorDetails(ex.getMessage());
        return  new ResponseEntity<>(myErrorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(com.nst.domaci.NST.exception.YearMismatch.class)
    private ResponseEntity<MyErrorDetails> handleYearMismatchException (YearMismatch ex){
        MyErrorDetails myErrorDetails = new MyErrorDetails(ex.getMessage());
        return  new ResponseEntity<>(myErrorDetails, HttpStatus.BAD_REQUEST);
    }
}
