package com.example.backend.entity;

import java.time.LocalDate;

import com.example.backend.entity.enums.EmpStatus;
import com.example.backend.security.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String phoneNumber;

    private LocalDate hireDate;

    @Enumerated(EnumType.STRING)
    private EmpStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToOne(mappedBy = "employee",cascade = CascadeType.ALL,orphanRemoval = true)
    private Receptionist receptionist;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL,orphanRemoval = true)
    private Veterinarians veterinarians;

    public record EmpRece(String phoneNumber,LocalDate hireDate,EmpStatus receptionistStatus,Receptionist.Rece workScheduleStatus){}
}
