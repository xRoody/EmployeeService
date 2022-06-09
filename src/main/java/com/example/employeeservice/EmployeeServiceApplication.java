package com.example.employeeservice;

import com.example.employeeservice.entities.Employee;
import com.example.employeeservice.repositories.EmployeeRepo;
import com.example.employeeservice.repositories.OrderRepo;
import com.example.employeeservice.repositories.WorkRepo;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class EmployeeServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context=SpringApplication.run(EmployeeServiceApplication.class, args);
        /*EmployeeRepo employeeRepo=context.getBean(EmployeeRepo.class);
        BCryptPasswordEncoder passwordEncoder=context.getBean(BCryptPasswordEncoder.class);
        Employee employee=new Employee();
        employee.setLogin("Bob");
        employee.setFName("Bob");
        employee.setLName("Dvich");
        employee.setPassword(passwordEncoder.encode("134652"));
        employee.setRole("EMP");
        employeeRepo.save(employee);*/
        /*WorkRepo workRepo=context.getBean(WorkRepo.class);
        workRepo.deleteAll();*/
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Module javaTimeModule() {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));//
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return module;
    }
}
