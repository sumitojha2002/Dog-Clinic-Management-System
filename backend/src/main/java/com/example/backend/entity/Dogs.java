package com.example.backend.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.backend.entity.enums.PetStatus;
import com.example.backend.entity.enums.VactionationStatus;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    private String imageUrl;
    private String name;
    private String breed;
    private String gender;
    private String color;

    private Double weight;

    @ElementCollection
    @CollectionTable(name="allergies",joinColumns = @JoinColumn(name="dog_id"))
    @Column(name="allergies")
    private Set<String> allergies = new HashSet<>();

    @ElementCollection
    @CollectionTable(name="chronicConditions",joinColumns = @JoinColumn(name="dog_id"))
    @Column(name="chronicConditions")
    private Set<String> chronicConditions = new HashSet<>();

    private String specialNotes;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private VactionationStatus vaccinationStatus;

    private LocalDate registeredDate;
    private LocalDate lastVisitDate;
   
    @Enumerated(EnumType.STRING)
    private PetStatus status;

    @OneToMany(mappedBy = "dogs",fetch = FetchType.LAZY)
    private List<Appointments> appointment =  new ArrayList<>();

    public record DogCardInfo(
        Long id,
        String imageURL,
        String name,
        LocalDate dateOfBirth,
        String breed
    ){}

    public record DogInner(
        Long id,
        String name,
        String imageUrl,
        String breed,
        String gender,
        String color,
        Double weight,
        LocalDate dateOfBirth,
        VactionationStatus vactionationStatus,
        Set<String> allergies,
        Set<String> chronicConditions,
        LocalDate registeredDate,
        LocalDate lastVisitDate,
        PetStatus status){}

    public record DogInfo(
        Long id,
        String name,
        String breed,
        String gender,
        String color,
        Double weight,
        LocalDate dateOfBirth,
        VactionationStatus vaccinationStatus,
        LocalDate registeredDate,
        LocalDate lastVisitDate,
        PetStatus status,
        Owners.OwnersProfile ownersProfile){}

    public record DogMedicalRecord(
        Long dogId,
        String name,
        String breed,
        String gender,
        String color,
        Double weight){}
}
