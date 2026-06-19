package com.example.backend.entity.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OwnerPetDTO {
    private String name;
    private String breed;
    private String gender;
    private String color;
    private LocalDate dateOfBirth;
}
