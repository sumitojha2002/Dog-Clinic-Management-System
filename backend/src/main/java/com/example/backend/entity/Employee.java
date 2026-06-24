package com.example.backend.entity;

import java.time.LocalDate;

import com.example.backend.entity.enums.EmpStatus;
import com.example.backend.security.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "employee",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Receptionist receptionist;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "employee",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Veterinarians veterinarians;

    public record EmpRece(User.userInfo userInfo,String phoneNumber,LocalDate hireDate,EmpStatus receptionistStatus,Receptionist.Rece workScheduleStatus){}

    public record EmpVet(String phoneNumber,LocalDate hireDate,EmpStatus vetStatus,Veterinarians.vet vetInfo){}

    // On records, you can't use @JsonInclude directly the usual way since they're records,
    // but Jackson respects it if placed on the record itself:
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Emp(String phoneNumer,LocalDate hireDate,EmpStatus status,User.userInfo userInfo,Receptionist.Rece receptionists,Veterinarians.vet veterinarians){};
}
