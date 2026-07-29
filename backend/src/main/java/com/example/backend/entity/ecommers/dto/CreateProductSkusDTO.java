package com.example.backend.entity.ecommers.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProductSkusDTO {
    @NotNull(message = "Size of the product cannot be empty.")
    private Long sizeOfProduct;

    @NotNull(message = "Color of the product cannot be empty.")
    private Long colorOfProduct;

    @NotEmpty(message = "SKU cannot be empty")
    private String sku;

    @NotNull(message = "Price cannot be empty.")
    private Double price;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Cannote be less then one.")
    private Long quantity;
}
