package com.a2823kevin.backend.repository;

import java.sql.CallableStatement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.a2823kevin.backend.exception.EmployeeNotFoundException;
import com.a2823kevin.backend.model.Employee;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Employee> getEmployees() {
        return jdbcTemplate.query("CALL getEmployees()", employeeRowMapper);
    }

    public Employee assignSeat(String empId, int floorSeatSeq) {
        jdbcTemplate.execute((ConnectionCallback<Object>) con -> {
            // use prepareCall to prevent SQL Injection
            try (CallableStatement cs = con.prepareCall("CALL assignSeat(?, ?)")) {
                cs.setString(1, empId);
                cs.setInt(2, floorSeatSeq);
                cs.execute();
            }
            return null;
        });

        return findEmployeeById(empId);
    }

    public Optional<Employee> findEmployeeByFloorSeatSeq(int floorSeatSeq) {
        List<Employee> result = jdbcTemplate.query(
            "CALL getEmployeeByFloorSeatSeq(?)", 
            employeeRowMapper, 
            floorSeatSeq
        );
        return result.stream().findFirst();
    }

    public Employee findEmployeeById(String empId) throws EmployeeNotFoundException {
        List<Employee> result = jdbcTemplate.query(
            "CALL getEmployeeById(?)",
            employeeRowMapper,
            empId
        );

        return result.stream().findFirst()
            .orElseThrow(() -> new EmployeeNotFoundException(empId));
    }

    private final RowMapper<Employee> employeeRowMapper = (rs, rowNum) -> {
        Employee emp = new Employee();
        emp.setEmpId(rs.getString("EMP_ID"));
        emp.setName(rs.getString("NAME"));
        emp.setEmail(rs.getString("EMAIL"));
        emp.setFloorNo(rs.getObject("FLOOR_NO") != null ? rs.getInt("FLOOR_NO") : null);
        emp.setSeatNo(rs.getObject("SEAT_NO") != null ? rs.getInt("SEAT_NO") : null);
        return emp;
    };
}
