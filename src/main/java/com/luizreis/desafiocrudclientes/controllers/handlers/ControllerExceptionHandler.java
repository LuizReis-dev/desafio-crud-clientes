package com.luizreis.desafiocrudclientes.controllers.handlers;

import com.luizreis.desafiocrudclientes.dto.CustomError;
import com.luizreis.desafiocrudclientes.dto.ValidationError;
import com.luizreis.desafiocrudclientes.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError error = new CustomError(Instant.now(),status.value(), e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError error = new ValidationError(Instant.now(),status.value(), "Invalid data",request.getRequestURI());
        for(FieldError f : e.getBindingResult().getFieldErrors()){
            error.addError(f.getField(),f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(error);
    }
}
