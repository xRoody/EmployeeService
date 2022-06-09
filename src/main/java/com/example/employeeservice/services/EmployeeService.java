package com.example.employeeservice.services;

import com.example.employeeservice.dtos.EmpDTO;
import com.example.employeeservice.dtos.WorkNodeDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface EmployeeService extends UserDetailsService {
    public EmpDTO getDto(String login);
    Long addWorkNode(WorkNodeDTO workNodeDTO);
    EmpDTO getDto(Long id);
    Long getIdByLogin(String login);
}
