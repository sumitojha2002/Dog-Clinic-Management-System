package com.example.backend.entity.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OwnerPetDTO {
    private MultipartFile imageUrl;
    @NotEmpty(message = "name of the dog is required.")
    private String name;
    @NotEmpty(message = "breed of the dog is required.")
    private String breed;
    @NotEmpty(message = "breed of the dog is required.")
    private String gender;
    private String color;
    private LocalDate dateOfBirth;
}
