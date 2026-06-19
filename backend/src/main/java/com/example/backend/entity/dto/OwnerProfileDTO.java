package com.example.backend.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OwnerProfileDTO {
    @NotNull(message = "It cannot be null.")
    @NotEmpty(message = "It cannot be empty.")
    @NotBlank(message = "It cannot be left blank.")
    public String phoneNumber;

    @NotNull(message = "It cannot be null.")
    @NotEmpty(message = "It cannot be empty.")
    @NotBlank(message = "It cannot be left blank.")
    public String alternatePhoneNumber;

    @NotNull(message = "It cannot be null.")
    @NotEmpty(message = "It cannot be empty.")
    @NotBlank(message = "It cannot be left blank.")
    private String address;
}
