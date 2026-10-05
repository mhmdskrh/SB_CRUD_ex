package com.example.employee_api.dto;

public record EmployeeRequest(
        String firstName,
        String lastName,
        String email,
        Long departmentId) {
}