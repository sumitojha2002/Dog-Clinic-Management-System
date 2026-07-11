package com.example.backend.entity.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class OwnerPetDTOUpdate {
    private MultipartFile imageUrl;
    private String name;
    private String breed ;
    private String gender ;
    private String color;
    private LocalDate dateOfBirth;
}
