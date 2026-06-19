package com.example.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.Dogs;
import com.example.backend.entity.Owners;
import com.example.backend.entity.dto.OwnerPetDTO;
import com.example.backend.entity.dto.OwnerProfileDTO;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.security.entity.User;
import com.example.backend.services.OwnerService;
import com.example.backend.services.UserServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner")
public class OwnerController {
    private final OwnerService ownerService;
    private final UserServices userServices;
    
    @PostMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@AuthenticationPrincipal UserDetails userDetails,@RequestBody OwnerProfileDTO ownerProfileDTO){
        User user = userServices.findUser(userDetails.getUsername()).orElseThrow(()-> new UserNotFoundException("User not found exception."));
        Owners owner = ownerService.findOwnerById(user.getId());
        return ownerService.updateOwnersProfile(ownerProfileDTO, owner);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> fetchOwnerProfile(@AuthenticationPrincipal UserDetails userDetails){
        User user = userServices.findUser(userDetails.getUsername()).orElseThrow(()-> new UserNotFoundException("User not found exception."));
        return userServices.getUserProfile(user);
    }

    @GetMapping("/dogs")
    public List<Dogs.DogInner> findAllDogs(@AuthenticationPrincipal UserDetails userDetails){
        User user = userServices.findUser(userDetails.getUsername()).orElseThrow(()-> new UserNotFoundException("could not find the user."));
        Owners owners = ownerService.findOwnerById(user.getId());
        return ownerService.getAllDogs(owners);
    }

    @GetMapping("/dogs/{id}")
    public ResponseEntity<?> findDogById(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        User user = userServices.findUser(username)
                                .orElseThrow(()-> 
                                new UserNotFoundException("User not found"));
        Owners owner = ownerService.findOwnerById(user.getId());
        return ownerService.findPetsByID(id, owner);
    }


    @PostMapping
    public ResponseEntity<?> addDog(@RequestBody OwnerPetDTO ownerPetDTO
                                    ,@AuthenticationPrincipal UserDetails userDetails){
        
            User user = userServices.findUser(userDetails
                                .getUsername())
                                .orElseThrow(()-> 
                                new UserNotFoundException("User not found"));
            Owners owners = ownerService.findOwnerById(user.getId());

           return ownerService.addPet(ownerPetDTO,owners);
    }
}

