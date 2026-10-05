package com.example.employee_api.service;

import java.util.*;
import java.util.Optional;

import javax.swing.Spring;

import org.hibernate.sql.Delete;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;
import com.example.employee_api.repository.*;
import com.example.employee_api.dto.*;
import com.example.employee_api.entity.*;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<EmployeeResponse> getAll() {
        return employeeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public EmployeeResponse getById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found"));
        return toResponse(employee);
    }

    public EmployeeResponse create(EmployeeRequest request) {
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found"));
        Employee employee = new Employee();
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setEmail(request.email());
        employee.setDepartment(department);
        return toResponse(employeeRepository.save(employee));
    }

    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found"));
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found"));
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setEmail(request.email());
        employee.setDepartment(department);
        return toResponse(employeeRepository.save(employee));
    }

    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found"));
        employeeRepository.delete(employee);
    }

    private EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartment().getId(),
                employee.getDepartment().getName());
    }
}