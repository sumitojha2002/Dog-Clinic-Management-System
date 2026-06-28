package com.example.backend.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data  
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="dog_id",referencedColumnName = "id")
    private Dogs dogs;

    @OneToOne
    @JoinColumn(name="vet_id",referencedColumnName = "id")
    private Veterinarians veterinarians;

    @OneToOne
    @JoinColumn(name="appointment_id",referencedColumnName = "id")
    private Appointments appointments;

    private String diagnosis;

    private String treatment;

    private String symptoms;

    private String notes;

    private LocalDate visitDate;
}
