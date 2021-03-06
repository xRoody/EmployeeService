package com.example.employeeservice.repositories;

import com.example.employeeservice.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Optional<Employee> findByLogin(String login);
}
