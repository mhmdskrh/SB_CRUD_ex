package com.example.employee_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.example.employee_api.dto.EmployeeRequest;
import com.example.employee_api.dto.EmployeeResponse;
import com.example.employee_api.service.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAll() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                employeeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> create(
            @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> update(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(
                employeeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}