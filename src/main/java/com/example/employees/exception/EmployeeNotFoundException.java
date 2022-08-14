package com.example.employees.exception;

public class EmployeeNotFoundException extends Exception{
    public EmployeeNotFoundException(Long id) {
        super("Employee with id "+id.toString()+" is not found");
    }
}
