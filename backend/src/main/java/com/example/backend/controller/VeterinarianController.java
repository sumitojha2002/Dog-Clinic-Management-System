package com.example.backend.controller;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.dto.DogsVetDTO;
import com.example.backend.entity.dto.MedicalRecordDTO;
import com.example.backend.entity.dto.VetDTO;
import com.example.backend.entity.dto.VetUpdateDTO;
import com.example.backend.services.VetServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vet")
@RequiredArgsConstructor
public class VeterinarianController {
    private final VetServices vetServices;

    @PostMapping(value="/profile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> setVetProfile(@AuthenticationPrincipal UserDetails userDetails,@Valid @ModelAttribute VetDTO vetDTO){
        return vetServices.setVetProfile(vetDTO,userDetails);
    }

    @PatchMapping("/profile")
    public ResponseEntity<?> updateVetProfile(@ModelAttribute VetUpdateDTO vetDTO,@AuthenticationPrincipal UserDetails userDetails){
        return vetServices.updateProile(vetDTO,userDetails);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getVetProfile(@AuthenticationPrincipal UserDetails userDetails){
        return vetServices.getVetProfile(userDetails);
    }

    @GetMapping("/appointments")
    public ResponseEntity<?> findAllthAppointmentsOfVetId(@AuthenticationPrincipal UserDetails userDetails){
        return vetServices.getTheAppoinementAccToVetId(userDetails);
    }

    // Vet sets then when he starts the appointment
    @PatchMapping("/appointments/{id}/start")
    public ResponseEntity<?> appointmentStatusToInProgress(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails){
        return vetServices.appointmentStatusToInProgress(id,userDetails);
    }

    @PostMapping("/appointments/{id}/medical_record")
    public ResponseEntity<?> setMedicalRecord(@PathVariable Long id,
        @Valid @RequestBody MedicalRecordDTO medicalRecordDTO,
        @AuthenticationPrincipal UserDetails userDetails){
        return vetServices.createMedicalRecord(id, medicalRecordDTO,userDetails);
    }

    @PostMapping("dog/{id}/profile")
    public ResponseEntity<?> setUpDogsProfile(@AuthenticationPrincipal UserDetails userDetails
        ,@Valid @RequestBody DogsVetDTO dogsVetDTO,@PathVariable Long id){
            return vetServices.setUpDogsProfile(dogsVetDTO, id, userDetails);
        }
}
