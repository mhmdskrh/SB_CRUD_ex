package com.example.employee_api.dto;

public record EmployeeResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Long departmentId,
        String departmentName) {
}