package com.example.backend.entity.ecommers.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UpdateProductDTO {
    private String name;
    private String description;
    private MultipartFile cover;
    private Long subCategoryId;
}
