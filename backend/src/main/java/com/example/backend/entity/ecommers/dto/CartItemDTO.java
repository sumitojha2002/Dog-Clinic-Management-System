package com.example.backend.entity.ecommers.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemDTO {
    @NotNull
    private Long skuId;

    @NotNull
    @Min(1)
    private Long quantity;
}
