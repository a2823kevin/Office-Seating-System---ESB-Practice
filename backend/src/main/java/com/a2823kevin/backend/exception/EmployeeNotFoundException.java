package com.a2823kevin.backend.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String empId) {
        super("Employee ID not found: " + empId);
    }
}