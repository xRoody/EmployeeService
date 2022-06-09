package com.example.employeeservice.services;

import com.example.employeeservice.dtos.OrderNodeDTO;
import com.example.employeeservice.dtos.WorkNodeDTO;
import com.example.employeeservice.entities.WorkNode;

import java.time.LocalDateTime;

public interface WorkService {
    WorkNodeDTO getDTOByObj(WorkNode x);
    void addOrderNode(OrderNodeDTO orderNodeDTO);

    Long save(WorkNode workNode);

    void close(Long id);
}
