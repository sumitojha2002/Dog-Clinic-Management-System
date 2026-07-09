package com.example.backend.entity.dto;

import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class VetUpdateDTO {

    private String name;
    
    private MultipartFile image;

    private String phoneNumber;

    private String licenseNumber;

    private Long yearsOfExperince;

    private Set<String> specialization;
}

