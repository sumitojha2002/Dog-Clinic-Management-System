package com.example.backend.entity.ecommers.dto;

import com.example.backend.entity.ecommers.ProductAttributes;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdatePorductSkusDTO {
    private ProductAttributes sizeOfProduct;

    private ProductAttributes colorOfProdudct;

    private String sku;

    private Double price;

    @Min(value = 1, message = "Cannote be less then one.")
    private Long quantity;
}
