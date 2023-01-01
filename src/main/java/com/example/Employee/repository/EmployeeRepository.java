package com.example.Employee.repository;

import com.example.Employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    public List<Employee> findAllByNameContainingIgnoreCase(String keyword);
}
