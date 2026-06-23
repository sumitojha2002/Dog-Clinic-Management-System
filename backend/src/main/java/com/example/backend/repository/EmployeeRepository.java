package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{
    
    @Query("""
        SELECT DISTINCT e FROM Employee e
        LEFT JOIN FETCH e.user
        LEFT JOIN FETCH e.veterinarians
        LEFT JOIN FETCH e.receptionist
    """)
    List<Employee> findAllEmployees();

    @Query("""
        SELECT DISTINCT e FROM Employee e
        LEFT JOIN FETCH e.user
        LEFT JOIN FETCH e.veterinarians
        LEFT JOIN FETCH e.receptionist
        WHERE e.user.id = :id
    """)
    List<Employee> findReceProfile(@Param("id") Long id);



}
