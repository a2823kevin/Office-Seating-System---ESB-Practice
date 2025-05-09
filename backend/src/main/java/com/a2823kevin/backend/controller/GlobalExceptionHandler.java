package com.a2823kevin.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.a2823kevin.backend.dto.ApiResponse;
import com.a2823kevin.backend.exception.EmployeeNotFoundException;
import com.a2823kevin.backend.exception.SeatNotFoundException;
import com.a2823kevin.backend.exception.SeatOccupiedException;

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

        @ExceptionHandler(SeatOccupiedException.class)
    public ResponseEntity<?> handleSeatOccupied(SeatOccupiedException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ApiResponse.error(ex.getMessage())
            );
    }
}