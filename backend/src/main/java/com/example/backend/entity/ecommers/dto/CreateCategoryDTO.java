package com.example.backend.entity.ecommers.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCategoryDTO {
    
    @NotEmpty(message = "Name is required.")
    @Size(max=30,message = "Name cannot be longer.")
    @Size(min=3,message ="Name is too short.")
    private String name;

    @NotEmpty(message = "Description is required")
    @Size(max=500,message = "Description cannot be more then 500 characters.")
    @Size(min=3,message ="Description is too short.")
    private String description;
}
