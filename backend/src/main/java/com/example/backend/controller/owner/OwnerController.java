package com.example.backend.controller.owner;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.Owners;
import com.example.backend.entity.dto.AppointmentDTO;
import com.example.backend.entity.dto.OwnerPetDTO;
import com.example.backend.entity.dto.OwnerPetDTOUpdate;
import com.example.backend.entity.dto.OwnerProfileDTO;
import com.example.backend.repository.OwnerRepository;
import com.example.backend.security.entity.User;
import com.example.backend.security.repository.UserRepository;
import com.example.backend.services.OwnerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner")
public class OwnerController {
    private final OwnerService ownerService;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepo;
   

    // add data in the profile
    @PostMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody OwnerProfileDTO ownerProfileDTO) {
        User user = userRepository.findByUsernameOrEmail(userDetails.getUsername()).get();

        Owners owner = ownerRepo.findByUserId(user.getId()).get();

        return ownerService.updateOwnersProfile(ownerProfileDTO, owner);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> fetchOwnerProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsernameOrEmail(userDetails.getUsername()).get();

        return ownerService.getOwnerProfile(user);
    }

    @GetMapping("/dogs")
    public ResponseEntity<?> findAllDogs(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsernameOrEmail(userDetails.getUsername()).get();
        Owners owners = ownerRepo.findByUserId(user.getId()).get();
        return ownerService.getAllDogs(owners);
    }

    @GetMapping("/dogs/{id}")
    public ResponseEntity<?> findDogById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsernameOrEmail(userDetails.getUsername()).get();
        Owners owner = ownerRepo.findByUserId(user.getId()).get();
        return ownerService.findPetsByID(id, owner);
    }

    @PostMapping(value = "/dogs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addDog(@Valid @ModelAttribute OwnerPetDTO ownerPetDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsernameOrEmail(userDetails.getUsername()).get();
        Owners owners = ownerRepo.findByUserId(user.getId()).get();
        return ownerService.addPet(ownerPetDTO, owners);
    }

    @PatchMapping(value="/dogs/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateDog(@ModelAttribute OwnerPetDTOUpdate ownerPetDTOUpdate,@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long id){
        return ownerService.updateDogsProfile(ownerPetDTOUpdate, id, userDetails);
    }

    // @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate
    @GetMapping("/available")
    public ResponseEntity<?> getAllAvailabeTime(
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate,@RequestParam(required = true) Long vetId) {
        return ownerService.getAppointmentTime(localDate,vetId);
    }

    // Appointment
    @PostMapping("/dogs/{id}/appointment")
    public ResponseEntity<?> appointmentForDog(
            @PathVariable Long id,
            @Valid @ModelAttribute AppointmentDTO appDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ownerService.setAppoinmentForThePet(id, appDto, userDetails);
    }

    @GetMapping("/dogs/{id}/appointment")
    public ResponseEntity<?> getAllAppointmentsDog(
        @PathVariable Long id,
        @AuthenticationPrincipal UserDetails userDetails){
        return ownerService.getAllAppointments(id,userDetails);
    }

    @GetMapping("/dogs/{id}/medical_records")
    public ResponseEntity<?> getMedicalRecord(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return ownerService.medicalRecord(id, userDetails);
    }

    @GetMapping("/vet-list")
    public ResponseEntity<?> getVetsList() {
        return ownerService.getVetsList();
    }



}



