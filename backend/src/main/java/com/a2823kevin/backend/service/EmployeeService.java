package com.a2823kevin.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.a2823kevin.backend.dto.EmployeeDto;
import com.a2823kevin.backend.exception.SeatOccupiedException;
import com.a2823kevin.backend.model.Employee;
import com.a2823kevin.backend.repository.EmployeeRepository;
import com.a2823kevin.backend.repository.SeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final SeatRepository seatRepository;

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.getEmployees()
            .stream()
            .map(emp->new EmployeeDto(emp))
            .toList();
    }

    public EmployeeDto assignSeatToEmployee(String empId, int floorSeatSeq) {
        // check if inputs employee & seat exist
        employeeRepository.findEmployeeById(empId);
        seatRepository.findSeatBySeq(floorSeatSeq);

        // check if seat is occupied
        Optional<Employee> e = employeeRepository.findEmployeeByFloorSeatSeq(floorSeatSeq);
        if (e.isPresent()) {
            throw new SeatOccupiedException(e.get().getName(), floorSeatSeq);
        }

        Employee emp = employeeRepository.assignSeat(empId, floorSeatSeq);
        return new EmployeeDto(emp);
    }
}
