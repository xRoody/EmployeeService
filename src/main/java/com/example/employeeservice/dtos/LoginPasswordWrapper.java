package com.example.employeeservice.dtos;

import lombok.Data;

@Data
public class LoginPasswordWrapper {
    private String login;
    public String password;
}
