package com.example.employeeservice.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class WorkNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_id")
    private Long id;
    @Column(name = "doo")
    private LocalDateTime doo;
    @Column(name = "doc")
    private LocalDateTime doc;
    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employee employee;
    @OneToMany(mappedBy = "workNode", cascade = CascadeType.ALL)
    private Set<OrderNode> orderNodes=new HashSet<>();
}
