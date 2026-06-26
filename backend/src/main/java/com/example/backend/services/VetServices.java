package com.example.backend.services;

import java.util.Iterator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.backend.entity.Veterinarians;
import com.example.backend.entity.dto.VetDTO;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.helper.ProfileHelper;
import com.example.backend.repository.VeterinarianRepository;
import com.example.backend.response.Response;
import com.example.backend.security.entity.User;
import com.example.backend.security.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VetServices {
    private final VeterinarianRepository vetRepo;
    private final UserRepository userRepo;
    // vet
    @Transactional
    public ResponseEntity<?> setVetProfile(VetDTO vetDTO,UserDetails userDetails){
        try{

            User user = userRepo
            .findByUsernameOrEmail(userDetails.getUsername()).get();
            
            Veterinarians vet  = vetRepo
            .findByUserId(user.getId())
            .orElseThrow(()-> new UserNotFoundException("Veterinarian not found."));
            
            vet.setLicenseNumber(vetDTO.getLicenseNumber());
            Iterator<String> str = vetDTO.getSpecialization().iterator();
            while(str.hasNext()){
                String item = str.next();
                System.out.println(item);
            }
            System.out.println(vetDTO.getSpecialization());
            vet.getSpecialization().addAll(vetDTO.getSpecialization());
            vet.setYearsOfExperience(vetDTO.getYearsOfExperince());
            
            vetRepo.save(vet);

            return Response.ResponseHandler(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED);
        }catch(UserNotFoundException e){
            e.printStackTrace();
            return Response.ResponseHandler(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getVetProfile(UserDetails userDetails){
        try{
            User user = userRepo.findByUsernameOrEmail(userDetails.getUsername()).get();
            Veterinarians.vetProfile vet = vetRepo.findVetByUserId(user.getId())
                .map(ProfileHelper::getVetProfile)
                .orElseThrow(()-> new UserNotFoundException("Veterinarian not found."));

            return Response.ResponseHandler(
                HttpStatus.FOUND.getReasonPhrase(),
                HttpStatus.FOUND, 
                vet);

        }catch(UserNotFoundException e){
            e.printStackTrace();
            return Response.ResponseHandler(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
