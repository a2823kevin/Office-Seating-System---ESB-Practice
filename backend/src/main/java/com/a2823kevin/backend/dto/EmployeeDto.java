package com.a2823kevin.backend.dto;

import com.a2823kevin.backend.model.Employee;

import lombok.Data;

@Data
public class EmployeeDto {
    private String empId;
    private String name;
    private String email;
    private Integer floorNo;
    private Integer seatNo;

    public EmployeeDto(Employee employee) {
        setEmpId(employee.getEmpId());
        setName(employee.getName());
        setEmail(employee.getEmail());
        setFloorNo(employee.getFloorNo());
        setSeatNo(employee.getSeatNo());
    }
}
