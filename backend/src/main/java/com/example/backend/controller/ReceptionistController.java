package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.dto.ReceDTO;
import com.example.backend.services.ReceptionistService;
import com.example.backend.services.UserServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/receptionist")
public class ReceptionistController {
    private final ReceptionistService receService;
    private final UserServices userServices;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        return receService.getReceProfile(username);
    }

    @PostMapping("/profile")
    public ResponseEntity<?> setUpProfile(@Valid @RequestBody ReceDTO receDTO,@AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        return receService.changeProfile(receDTO,username);
    }
}
