package com.example.employeeservice.dtos;

import com.example.employeeservice.entities.WorkNode;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class EmpDTO {
    private Long id;
    private String login;
    private String password;
    private String role;
    private String fName;
    private String lName;
    private Set<WorkNodeDTO> nodes=new HashSet<>();
}
