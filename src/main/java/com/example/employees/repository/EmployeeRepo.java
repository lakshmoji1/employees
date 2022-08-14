package com.example.employees.repository;

import com.example.employees.entity.Address;
import com.example.employees.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    Optional<Employee> findEmployeeById(Long id);
    List<Employee> findByNameLike(String name);
    @Query("SELECT new com.example.employees.entity.Address(a.id, a.city, a.district, a.town, a.street, a.isDefault) FROM Employee e JOIN e.addressList a where e.id = ?1")
    public List<Address> getAddressByUserId(Long id);
}