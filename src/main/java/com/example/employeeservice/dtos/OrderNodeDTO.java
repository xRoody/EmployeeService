package com.example.employeeservice.dtos;


import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderNodeDTO {
    private Long id;
    private Long orderId;
    private LocalDateTime doe;
    private Long workId;
}
