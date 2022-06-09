package com.example.employeeservice.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BodyExceptionWrapper {
    private String code;
    private String reason;
}
