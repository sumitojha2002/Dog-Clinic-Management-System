package com.example.backend.entity;

import java.util.List;

import com.example.backend.security.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity 
public class Veterinarians {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;   

    @Column(unique = true)
    private String licenseNumber;

    private List<String> specialization;

    private Long yearsOfExperience;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="emp_id",referencedColumnName = "id")
    private Employee employee;
}
