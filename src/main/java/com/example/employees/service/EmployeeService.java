package com.example.employees.service;

import java.util.List;
import java.util.UUID;

import com.example.employees.entity.Address;
import com.example.employees.entity.Employee;
import com.example.employees.exception.EmployeeNotFoundException;
import com.example.employees.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepo.findAll();
    }

    public List<Employee> searchEmployee(String name) {
        return employeeRepo.findByNameLike("%" + name + "%");
    }

    public Employee updateEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public Employee findEmployeeById(Long id) throws EmployeeNotFoundException {
        if(employeeRepo.findEmployeeById(id).isPresent())
            return employeeRepo.findEmployeeById(id).get();
        else
            throw new EmployeeNotFoundException(id);
    }

    public List<Address> getAddressByUserId(Long id) {
        return employeeRepo.getAddressByUserId(id);
    }

    public void deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
    }

}