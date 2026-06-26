package com.example.backend.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.backend.entity.enums.AppointmentStatus;

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
public class Appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="dog_id",referencedColumnName = "id")
    private Dogs dogs;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="vet_id",referencedColumnName = "id")
    private Veterinarians veterinarians;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="owner_id",referencedColumnName = "id")
    private Owners owners;

    private String reason;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    public record Appointment(
        Long id,
        Dogs.DogInner dogs,
        String reason,
        AppointmentStatus appointmentStatus,
        Veterinarians.vetProfile vets,
        Owners.OwnersProfile ownersProfile,
        LocalDate appointmentDate,
        LocalTime appLocalTime
    ) {}
}
