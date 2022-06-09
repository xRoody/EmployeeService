package com.example.employeeservice.repositories;

import com.example.employeeservice.entities.OrderNode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<OrderNode, Long> {
}
