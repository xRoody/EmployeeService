package com.example.employeeservice.dtos;

import com.example.employeeservice.entities.Employee;
import com.example.employeeservice.entities.OrderNode;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class WorkNodeDTO {
    private Long id;
    private LocalDateTime doo;
    private LocalDateTime doc;
    private Long employeeId;
    private Set<OrderNodeDTO> orderNodes;
}
