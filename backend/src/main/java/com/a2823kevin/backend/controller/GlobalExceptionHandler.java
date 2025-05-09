package com.a2823kevin.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.a2823kevin.backend.dto.ApiResponse;
import com.a2823kevin.backend.exception.EmployeeNotFoundException;
import com.a2823kevin.backend.exception.SeatNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<?> handleEmployeeNotFound(EmployeeNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ApiResponse.error(ex.getMessage())
            );
    }

    @ExceptionHandler(SeatNotFoundException.class)
    public ResponseEntity<?> handleSeatNotFound(SeatNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                ApiResponse.error(ex.getMessage())
            );
    }
}