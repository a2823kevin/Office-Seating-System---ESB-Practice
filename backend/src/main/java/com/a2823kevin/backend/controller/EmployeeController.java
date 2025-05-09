package com.a2823kevin.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a2823kevin.backend.dto.ApiResponse;
import com.a2823kevin.backend.dto.EmployeeDto;
import com.a2823kevin.backend.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllEmployees() {
        List<EmployeeDto> emps = employeeService.getAllEmployees();
        return ResponseEntity.ok(
            ApiResponse.success(
                String.format("find %d seats.", emps.size()), 
                emps
            )
        );
    }

    @PutMapping("/{empId}/seat")
    public ResponseEntity<?> updateEmployeeSeat(@PathVariable String empId, @RequestBody int floorSeatSeq) {
        EmployeeDto emp = employeeService.assignSeatToEmployee(empId, floorSeatSeq);
        return ResponseEntity.ok(
            ApiResponse.success(
                String.format("Employee %s's seat has been updated successfully.", emp.getName()), 
                emp
            )
        );
    }
}
