package com.example.employeeservice.repositories;

import com.example.employeeservice.entities.WorkNode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepo extends JpaRepository<WorkNode,Long> {
}
