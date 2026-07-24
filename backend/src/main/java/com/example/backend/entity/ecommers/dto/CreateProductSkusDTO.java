package com.example.backend.entity.ecommers.dto;

import com.example.backend.entity.ecommers.ProductAttributes;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateProductSkusDTO {
    @NotEmpty(message = "Size of the product cannot be empty.")
    private ProductAttributes sizeOfProduct;

    @NotEmpty(message = "Color of the product cannot be empty.")
    private ProductAttributes colorOfProdudct;

    @NotEmpty(message = "SKU cannot be empty")
    private String sku;

    @NotEmpty(message = "Price cannot be empty.")
    private Double price;

    @NotEmpty(message = "quantitiy cannot be emprty.")
    @Min(value = 1, message = "Cannote be less then one.")
    private Long quantity;
}
