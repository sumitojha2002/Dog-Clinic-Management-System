package com.example.backend.entity.dto;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VetDTO {
    
    private MultipartFile image;

    @NotEmpty(message = "Phone number cannot be empty.")
    @NotNull(message ="Phone number cannot be null.")
    @Size(min = 10, max=10, message="must contain 10 digits.")
    private String phoneNumber;

    @NotNull(message="License number cannot be null.")
    @NotEmpty(message="License number cannot be empty.")
    @Size(min=5, max=15, message="License number must be between > 5 and less then < 15")
    private String licenseNumber;

    @Min(value = 0, message="Must be greater then 0 yrs")
    @Max(value = 50, message="Cannot be more then 50 yrs")
    private Long yearsOfExperince;

    private Set<String> specialization;
}
