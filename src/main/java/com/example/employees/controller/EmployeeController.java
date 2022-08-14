package com.example.employees.controller;

import com.example.employees.entity.Address;
import com.example.employees.entity.Employee;
import com.example.employees.exception.EmployeeNotFoundException;
import com.example.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.findAllEmployees();
        return new ResponseEntity<>(employees,HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) throws EmployeeNotFoundException {
        try
        {
            Employee employee = employeeService.findEmployeeById(id);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            throw new EmployeeNotFoundException(id);
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<Employee>> search(@PathVariable("name") String name)
    {
        System.out.println("Searching for name that contains "+name);
        List<Employee> employees = new ArrayList<Employee>();
        employees = employeeService.searchEmployee(name);
        return new ResponseEntity<>(employees,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee newEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        Employee updateEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id)
    {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/add/address/{id}")
    public Employee addNewAddress(@PathVariable("id") Long id, @RequestBody Address newAddress) throws EmployeeNotFoundException {
        Employee employee = employeeService.findEmployeeById(id);
        if(employee != null) {
            employee.getAddressList().add(newAddress);
            return employeeService.updateEmployee(employee);
        }
        else {
            return null;
        }
    }

    @GetMapping("getAddressByUserId/{id}")
    public List<Address> findAllAddressesOfUser(@PathVariable("id") Long id) {
        return employeeService.getAddressByUserId(id);
    }

    @GetMapping("/setAsDefaultAddress/user/{employeeId}/{addressId}")
    public Address setDefaultAddress(@PathVariable("employeeId") Long employeeId, @PathVariable("addressId") Integer addressId) throws EmployeeNotFoundException {
        Employee employee = employeeService.findEmployeeById(employeeId);
        Address currentAddress = null;
        if(employee!=null) {
            List<Address> empAddresses = employee.getAddressList();
            for(Address address: empAddresses) {
                if(address.getId() == addressId) {
                    currentAddress = address;
                    currentAddress.setIsDefault(true);
                }
                else
                    address.setIsDefault(false);
            }
            employeeService.updateEmployee(employee);
        }
        return currentAddress;
    }

    @GetMapping("/getDefaultAddress/user/{employeeId}")
    public Address getDefaultAddress(@PathVariable("employeeId") Long employeeId) throws EmployeeNotFoundException {
        Employee emp = employeeService.findEmployeeById(employeeId);
        if(emp!=null){
            List<Address> empAddressList = emp.getAddressList();
            for(Address address: empAddressList)
            {
                if(address.getIsDefault())
                    return address;
            }
        }
        return null;
    }
}

