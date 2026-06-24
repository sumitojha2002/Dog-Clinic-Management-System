package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.dto.ReceDTO;
import com.example.backend.security.entity.User;
import com.example.backend.security.repository.UserRepository;
import com.example.backend.services.ReceptionistService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/receptionist")
public class ReceptionistController {
    private final ReceptionistService receService;
    private final UserRepository userRepo;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails){
        User user = userRepo.findByUsernameOrEmail(userDetails.getUsername()).get();
        return receService.getReceProfile(user.getId());
    }

    @PostMapping("/profile")
    public ResponseEntity<?> setUpProfile(@Valid @RequestBody ReceDTO receDTO,@AuthenticationPrincipal UserDetails userDetails){
        User user = userRepo.findByUsernameOrEmail(userDetails.getUsername()).get();
        return receService.changeProfile(receDTO,user.getId());
    }

    @GetMapping("/owners")
    public ResponseEntity<?> getAllTheOwners(){
        return receService.getOwnersProfiles();
    }

    @GetMapping("/owners/{id}")
    public ResponseEntity<?> getTheOwnersProfileById(@PathVariable Long id){
        return receService.getOwnersProfilesbyId(id);
    }

    // dogs
    // @GetMapping("/dogs")
    // public ResponseEntity<?> getAllDogsInfo(@RequestParam(required = false) String name,@RequestParam(required = false) String breed){
    //     return receService.getAllDogs(name,breed);
    // }


    // appointments
    @PatchMapping("/appointments/{id}/check-in")
    public ResponseEntity<?> changeAppCheckIn(@PathVariable Long id){
        return receService.changeAppCheckIn(id);
    }

    @PatchMapping("/appointments/{id}/confirmed")
    public ResponseEntity<?> changeAppConfirmend(@PathVariable Long id){
        return receService.changeAppConfirm(id);
    }
}
