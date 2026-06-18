package com.example.backend.services;

import org.springframework.stereotype.Service;

import com.example.backend.repository.OwnerRepository;
import com.example.backend.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServices {
    private final UserRepository userRepo;
    private final OwnerRepository ownerRepo;

    public boolean deleteById(Long id){
        try{
            
            userRepo.deleteById(id);
            return true;
        }catch(Exception e){
            throw e;
        }
    }
}
