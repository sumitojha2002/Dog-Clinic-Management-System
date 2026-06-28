package com.example.backend.entity.dto;

import java.util.Set;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class DogsVetDTO {
    @Min(value = 2, message = "Does weight in kgs. Cannot be negative.")
    private Double weight;
    private Set<String> allergies;
    private Set<String> chronicConditions;
    private String specialNotes;
}
