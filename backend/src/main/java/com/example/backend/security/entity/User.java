package com.example.backend.security.entity;

import com.example.backend.entity.Employee;
import com.example.backend.entity.Owners;
import com.example.backend.entity.Receptionist;
import com.example.backend.entity.Veterinarians;
import com.example.backend.security.entity.enums.Roles;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private Owners owners;
    
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private RefreshToken refreshToken;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private Employee employee;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private Receptionist receptionist;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private Veterinarians veterinarians;

    public record getUserProfile(String username,String email,Owners.OwnersProfile owners){};
}
