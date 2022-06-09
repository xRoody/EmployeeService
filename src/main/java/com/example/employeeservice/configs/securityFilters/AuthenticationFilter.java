package com.example.employeeservice.configs.securityFilters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.employeeservice.dtos.EmpDTO;
import com.example.employeeservice.dtos.LoginPasswordWrapper;
import com.example.employeeservice.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@PropertySource("classpath:application.properties")
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private final static ObjectMapper objectMapper=new ObjectMapper();

    public static final Algorithm algorithm=Algorithm.HMAC256("9t7%C[XZpn=m;xP4".getBytes());

    private EmployeeService employeeService;

    public AuthenticationFilter(AuthenticationManager authenticationManager, EmployeeService employeeService) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.employeeService = employeeService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username;
        String password;
        try{
            LoginPasswordWrapper loginFormDTO=objectMapper.readValue(request.getReader(),LoginPasswordWrapper.class);
            username=loginFormDTO.getLogin();
            password=loginFormDTO.getPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user=(User)authResult.getPrincipal();
        String accessToken= JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+20*60*1000))
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        String refreshToken=JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+20*60*60*1000))
                .withClaim("roles",Stream.of("REFRESHER").collect(Collectors.toList()))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        EmpDTO empDTO=employeeService.getDto(user.getUsername());
        Map<String,String> tokens=new HashMap<>();
        tokens.put("id", empDTO.getId().toString());
        tokens.put("username", empDTO.getFName()+" "+empDTO.getLName());
        tokens.put("access", accessToken);
        tokens.put("refresh", refreshToken);
        objectMapper.writeValue(response.getWriter(), tokens);
    }
}
