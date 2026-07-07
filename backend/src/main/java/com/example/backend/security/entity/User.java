package com.example.backend.security.entity;

import com.example.backend.entity.Employee;
import com.example.backend.entity.Owners;
import com.example.backend.security.entity.enums.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;

    public record getOwnerProfile(String username,String email,Owners.OwnersProfile owners){};
    
    public record getReceProfile(String username,String email,Employee.EmpRece emp){};

    public record getEmpProfile(String username,String email,Employee.Emp emp){};

    public record userInfo(String username, String email) {
    };
    
}
