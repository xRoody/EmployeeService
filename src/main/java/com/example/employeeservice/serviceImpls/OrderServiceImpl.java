package com.example.employeeservice.serviceImpls;

import com.example.employeeservice.dtos.OrderNodeDTO;
import com.example.employeeservice.entities.OrderNode;
import com.example.employeeservice.repositories.OrderRepo;
import com.example.employeeservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;

    @Override
    public OrderNodeDTO getDTOByObj(OrderNode y) {
        return OrderNodeDTO.builder()
                .id(y.getId())
                .orderId(y.getOrderId())
                .doe(y.getDoe())
                .workId(y.getWorkNode().getId())
                .build();
    }
}
