package com.example.backend.entity.ecommers.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentRequestDTO {
    @NotNull(message= "orderId cannot be null.")
    private Long orderId;
}
