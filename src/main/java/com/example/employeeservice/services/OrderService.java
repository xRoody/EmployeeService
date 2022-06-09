package com.example.employeeservice.services;

import com.example.employeeservice.dtos.OrderNodeDTO;
import com.example.employeeservice.entities.OrderNode;

public interface OrderService {
    OrderNodeDTO getDTOByObj(OrderNode y);

}
