package com.example.backend.entity;

import com.example.backend.entity.enums.ReceShiftStatus;
import com.example.backend.security.entity.User;


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

@Data
@Entity
public class Receptionist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="employee_id",referencedColumnName = "id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private ReceShiftStatus shift;
}
