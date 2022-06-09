package com.example.employeeservice.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.employeeservice.dtos.EmpDTO;
import com.example.employeeservice.dtos.OrderNodeDTO;
import com.example.employeeservice.dtos.WorkNodeDTO;
import com.example.employeeservice.entities.Employee;
import com.example.employeeservice.repositories.EmployeeRepo;
import com.example.employeeservice.services.EmployeeService;
import com.example.employeeservice.services.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.employeeservice.configs.securityFilters.AuthenticationFilter.algorithm;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WorkController {
    private final WorkService workService;
    private final EmployeeService employeeService;

    private final EmployeeRepo employeeRepo;

    @PostMapping("/add")
    public ResponseEntity<Object> addWorkNode(@RequestBody WorkNodeDTO workNodeDTO){
        Long id=employeeService.addWorkNode(workNodeDTO);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/orders")
    public ResponseEntity<Object> addOrderNode(@RequestBody OrderNodeDTO orderNodeDTO){
        workService.addOrderNode(orderNodeDTO);
        return ResponseEntity.ok(1);
    }

    @GetMapping("/employee/{id}/nodes")
    public ResponseEntity<Object> getAllNodes(@PathVariable("id") Long id){
        EmpDTO empDTO=employeeService.getDto(id);
        if(empDTO==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(empDTO.getNodes());
    }

    @PatchMapping("/work/{id}")
    public ResponseEntity<Object> closeWork(@PathVariable("id") Long id){
        workService.close(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/refresh")
    public ResponseEntity<Object> refresh(HttpServletRequest request){
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring("Bearer ".length());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                String username = decodedJWT.getSubject();
                EmpDTO empDTO=employeeService.getDto(username);
                String accessToken = JWT.create()
                        .withSubject(username)
                        .withExpiresAt(new Date(System.currentTimeMillis() + 20 * 60 * 1000))
                        .withClaim("roles", Stream.of(empDTO.getRole()).collect(Collectors.toList()))
                        .withIssuer(request.getRequestURL().toString())
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("id", empDTO.getId().toString());
                tokens.put("username", empDTO.getFName()+" "+empDTO.getLName());
                tokens.put("access", accessToken);
                tokens.put("refresh", token);
                return ResponseEntity.ok(tokens);
            } catch (Exception e) {
                return new ResponseEntity<>(new BodyExceptionWrapper("403", "Something wrong with JWT refresh token"), HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(new BodyExceptionWrapper("403", "Authorization is wrong or absent"), HttpStatus.FORBIDDEN);
    }
}
