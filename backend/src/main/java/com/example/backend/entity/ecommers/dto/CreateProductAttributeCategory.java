package com.example.backend.entity.ecommers.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateProductAttributeCategory {

    @NotEmpty(message = "ProductAttribute is required")
    private String productAttributesType;

    @NotEmpty(message = "Value cannot be empty.")
    private String value;
}
