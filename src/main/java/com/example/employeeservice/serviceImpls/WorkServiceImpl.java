package com.example.employeeservice.serviceImpls;

import com.example.employeeservice.dtos.OrderNodeDTO;
import com.example.employeeservice.dtos.WorkNodeDTO;
import com.example.employeeservice.entities.OrderNode;
import com.example.employeeservice.entities.WorkNode;
import com.example.employeeservice.repositories.WorkRepo;
import com.example.employeeservice.services.OrderService;
import com.example.employeeservice.services.WorkService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkServiceImpl implements WorkService {
    private final WorkRepo workRepo;
    private final OrderService orderService;

    @Override
    public WorkNodeDTO getDTOByObj(WorkNode x) {
        return WorkNodeDTO.builder()
                .id(x.getId())
                .doc(x.getDoc())
                .doo(x.getDoo())
                .employeeId(x.getEmployee().getId())
                .orderNodes(x.getOrderNodes().stream().map(y -> orderService.getDTOByObj(y)).collect(Collectors.toSet()))
                .build();
    }

    public void addOrderNode(OrderNodeDTO orderNodeDTO) {
        WorkNode workNode = workRepo.getById(orderNodeDTO.getWorkId());
        OrderNode orderNode = new OrderNode();
        orderNode.setWorkNode(workNode);
        orderNode.setOrderId(orderNodeDTO.getOrderId());
        orderNode.setDoe(orderNodeDTO.getDoe());
        workNode.getOrderNodes().add(orderNode);
    }

    @Override
    public Long save(WorkNode workNode) {
        return workRepo.save(workNode).getId();
    }

    @Override
    public void close(Long id) {
        if (workRepo.existsById(id)) {
            WorkNode node = workRepo.getById(id);
            node.setDoc(LocalDateTime.now());
            workRepo.save(node);
        }
    }
}
