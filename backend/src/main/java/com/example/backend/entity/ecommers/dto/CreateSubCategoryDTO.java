package com.example.backend.entity.ecommers.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateSubCategoryDTO {
    
    @NotNull(message="Id cannot be left empty.")
    private Long categoryId;

    @NotEmpty(message= "Name cannot be left empty.")
    @Size(min=3,message="Name is too short.")
    @Size(max=30,message="Name is too long.")
    private String name;
    
    @NotEmpty(message="Description cannot be left empty.")
    @Size(min=10, message="Description is too short.")
    @Size(max=300, message="Description is too long.")
    private String description;

}
