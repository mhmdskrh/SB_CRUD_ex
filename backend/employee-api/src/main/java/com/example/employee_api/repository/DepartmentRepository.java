package com.example.employee_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employee_api.entity.Department;

public interface DepartmentRepository
                extends JpaRepository<Department, Long> {
}