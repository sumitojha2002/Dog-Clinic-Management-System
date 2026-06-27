package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.dto.VetDTO;
import com.example.backend.services.VetServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vet")
@RequiredArgsConstructor
public class VeterinarianController {
    private final VetServices vetServices;

    @PostMapping("/profile")
    public ResponseEntity<?> setVetProfile(@AuthenticationPrincipal UserDetails userDetails,@Valid @RequestBody VetDTO vetDTO){
        return vetServices.setVetProfile(vetDTO,userDetails);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getVetProfile(@AuthenticationPrincipal UserDetails userDetails){
        return vetServices.getVetProfile(userDetails);
    }

    @GetMapping("/appointments")
    public ResponseEntity<?> findAllthAppointmentsOfVetId(@AuthenticationPrincipal UserDetails userDetails){
        return vetServices.getTheAppoinementAccToVetId(userDetails);
    }

    
}
