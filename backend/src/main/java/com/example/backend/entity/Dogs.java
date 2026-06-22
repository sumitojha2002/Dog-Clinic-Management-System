package com.example.backend.entity;

import java.time.LocalDate;

import com.example.backend.entity.enums.PetStatus;
import com.example.backend.entity.enums.VactionationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Dogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id",nullable=false)
    private Owners owners;

    private String name;
    private String breed;
    private String gender;
    private String color;

    private Double weight;

    private String allergies;
    private String chronicConditions;
    private String specialNotes;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private VactionationStatus vaccinationStatus;

    private LocalDate registeredDate;
    private LocalDate lastVisitDate;
   
    @Enumerated(EnumType.STRING)
    private PetStatus status;

    public record DogInner(Long id,String name,String breed,String gender,String color){}
}
