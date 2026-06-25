package com.example.backend.entity.dto;


import java.time.LocalDate;
import java.time.LocalTime;

import com.example.backend.helper.validator.ValidAppoinement;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@ValidAppoinement
public class AppointmentDTO {
    
    @Size(max=300, min=10, message = "Reason is too short.")
    private String reason;

     @NotNull(message ="Date is required")
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
     private LocalDate appointmentDate;
    
     @NotNull(message ="Time is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
     private LocalTime appointmentTime;

     @NotNull(message = "Veterinarian is required.")
     private Long vetId;
}
