package com.example.backend.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.exception.UserNotFoundException;
import com.example.backend.response.Response;
import com.example.backend.security.entity.User;
import com.example.backend.security.repository.UserRepository;
import com.example.backend.services.AdminServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminServices adminService;
    private final UserRepository userRepo;

    @GetMapping("/owners")
    public ResponseEntity<?> findAllOwners(){
        return adminService.getAllOwners();
    }    

    @GetMapping("/owners/{id}")
    public ResponseEntity<?> findOwnerById(@PathVariable Long id){
        return adminService.getOwnerById(id);
    }
    
    @GetMapping("/dogs")
    public ResponseEntity<?> findAllDogs(@RequestParam(required = false) String name,@RequestParam(required = false) String breed ){
        return adminService.getAllDogsRecord(name,breed);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){

        String username = userDetails.getUsername();
        User user = userRepo.findById(id).orElseThrow(()-> new UserNotFoundException("User not found of this id."));
        
        if(user.getUsername().equals(username)){
            return ResponseEntity.badRequest().body(Map.of("message","Cannot delete admin."));
        }
        try{
            adminService.deleteById(id);
            return Response.ResponseHandler("User deleted successfully.", HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("message","Cannot detete the user."));
        }
    }

}
