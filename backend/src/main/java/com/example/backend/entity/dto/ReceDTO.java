package com.example.backend.entity.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReceDTO {

    @NotEmpty(message = "Phone number cannot be empty.")
    @NotNull(message ="Phone number cannot be null.")
    @Size(min = 10, max=10, message="must contain 10 digits.")
    private String phoneNumber;

}
