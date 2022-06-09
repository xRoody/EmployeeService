package com.example.employeeservice.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "node_id")
    private Long id;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "doe")
    private LocalDateTime doe;
    @ManyToOne
    @JoinColumn(name = "work_id")
    private WorkNode workNode;
}
