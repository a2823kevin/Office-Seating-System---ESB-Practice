package com.a2823kevin.backend.model;

import lombok.Data;

@Data
public class Employee {
    private String empId;
    private String name;
    private String email;
    private Integer floorNo;
    private Integer seatNo;
}