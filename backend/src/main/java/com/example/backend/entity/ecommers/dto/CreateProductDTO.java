package com.example.backend.entity.ecommers.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateProductDTO {
   
    @NotEmpty(message = "Name cannot be empty.")
    private String name;

    @NotEmpty(message = "Description cannot be empty.")
    private String description;

    @NotEmpty(message = "Summery cannot be empty.")
    private MultipartFile cover;
    
    @NotEmpty(message = "SubCategory id is required.")
    private Long subCategoryId;

}
