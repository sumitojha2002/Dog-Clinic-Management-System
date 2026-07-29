package com.example.backend.entity.ecommers.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdatePorductSkusDTO {
    private Long sizeOfProduct;

    private Long colorOfProdudct;

    private String sku;

    private Double price;

    @Min(value = 1, message = "Cannote be less then one.")
    private Long quantity;
}
