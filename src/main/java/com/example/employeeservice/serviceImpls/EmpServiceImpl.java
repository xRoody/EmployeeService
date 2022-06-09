package com.example.employeeservice.serviceImpls;

import com.example.employeeservice.dtos.EmpDTO;
import com.example.employeeservice.dtos.WorkNodeDTO;
import com.example.employeeservice.entities.Employee;
import com.example.employeeservice.entities.WorkNode;
import com.example.employeeservice.repositories.EmployeeRepo;
import com.example.employeeservice.services.EmployeeService;
import com.example.employeeservice.services.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpServiceImpl implements EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final WorkService workService;



    public EmpDTO getDto(Long id){
        Employee employee=employeeRepo.getById(id);
        return EmpDTO.builder()
                .id(id)
                .login(employee.getLogin())
                .password(employee.getPassword())
                .role(employee.getRole())
                .fName(employee.getFName())
                .lName(employee.getLName())
                .nodes(employee.getNodes().stream().map(x->workService.getDTOByObj(x)).collect(Collectors.toSet()))
                .build();
    }

    public EmpDTO getDto(String login){
        Employee employee=employeeRepo.findByLogin(login).get();
        return EmpDTO.builder()
                .id(employee.getId())
                .login(employee.getLogin())
                .password(employee.getPassword())
                .role(employee.getRole())
                .fName(employee.getFName())
                .lName(employee.getLName())
                .nodes(employee.getNodes().stream().map(x->workService.getDTOByObj(x)).collect(Collectors.toSet()))
                .build();
    }

    public Long getIdByLogin(String login){
        return employeeRepo.findByLogin(login).get().getId();
    }

    public Long addWorkNode(WorkNodeDTO workNodeDTO){
        Employee employee=employeeRepo.getById(workNodeDTO.getEmployeeId());
        WorkNode workNode=new WorkNode();
        workNode.setEmployee(employee);
        workNode.setDoo(workNodeDTO.getDoo());
        workNode.setDoc(workNodeDTO.getDoc());
        return workService.save(workNode);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee=employeeRepo.findByLogin(username).orElseThrow(()->new UsernameNotFoundException(username));
        Collection<GrantedAuthority> authorities= Stream.of(employee.getRole()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new User(username, employee.getPassword(), authorities);
    }
}
