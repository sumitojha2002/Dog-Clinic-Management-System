package com.example.backend.entity.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MedicalRecordDTO {
    @NotNull(message ="Cannot set diagnosis null.")
    private String diagnosis;

    @NotNull(message ="Cannot set treatment null.")
    private String treatment;

    @NotNull(message ="Cannot set  symptoms null.")
    private String symptoms;

    @NotNull(message ="Cannot set  notes null.")
    private String notes;
}
