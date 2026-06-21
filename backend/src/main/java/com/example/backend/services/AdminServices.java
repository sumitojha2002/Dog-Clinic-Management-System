package com.example.backend.services;

import com.example.backend.security.entity.User;
import com.example.backend.security.entity.enums.Roles;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.entity.Dogs;
import com.example.backend.entity.Owners;
import com.example.backend.entity.Receptionist;
import com.example.backend.entity.enums.ReceShiftStatus;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.repository.DogsRepository;
import com.example.backend.repository.OwnerRepository;
import com.example.backend.repository.ReceptionistRepository;
import com.example.backend.response.Response;
import com.example.backend.security.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServices {
    private final UserRepository userRepo;
    private final OwnerRepository ownerRepo;
    private final DogsRepository dogsRepo;
    private final ReceptionistRepository receptRepo;

    @Transactional
    public boolean deleteById(Long id){
        try{
            userRepo.deleteById(id);
            return true;
        }catch(Exception e){
            throw e;
        }
    }

    public ResponseEntity<?> getAllOwners(){
        try{
            List<User.getOwnerProfile> ownersProfile = userRepo.findByRoles(Roles.ROLE_OWNER)
        .stream()
        .map(user -> {
            Owners o = user.getOwners();
            Owners.OwnersProfile profile = (o != null)
                    ? new Owners.OwnersProfile(
                            o.getId(),
                            o.getPhoneNumber(),
                            o.getAlternatePhoneNumber(),
                            o.getAddress(),
                            o.getRegistrationDate())
                    : new Owners.OwnersProfile(
                            null, null, null, null, null); 

            return new User.getOwnerProfile(
                    user.getUsername(),
                    user.getEmail(),
                    profile);
        })
        .toList();
        
            if(ownersProfile.isEmpty()){
                return Response.ResponseHandler("There are no owners.", HttpStatus.NOT_FOUND);
            }else{
                return Response.ResponseHandler("All owners fetched successfully.", HttpStatus.OK, ownersProfile);
            }
        }catch(Exception e){
            e.printStackTrace();    
            return Response.ResponseHandler("Somthing went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllDogsRecord(String name,String breed){
        try{
            String safeName = (name != null && !name.isEmpty()) ? name : null;
            String safeBreed = (breed != null && !breed.isEmpty()) ? breed: null;
            
            List<Dogs.DogInner> dogs = dogsRepo.findAllDogsByNameAndBreed(safeBreed,safeName)
                                                .stream()
                                                .map(dog-> new Dogs.DogInner(dog.getId(), 
                                                                            dog.getName(), 
                                                                            dog.getBreed(), 
                                                                            dog.getGender(), 
                                                                            dog.getColor())).toList();

                if(dogs.isEmpty()){
                    return Response.ResponseHandler("No dogs found.", HttpStatus.NOT_FOUND);
                }else{
                    return Response.ResponseHandler("All the dogs found succcessfully.", HttpStatus.OK, dogs);
                }
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getOwnerById(Long id){
        try{
            Optional<User.getOwnerProfile> ownerById = userRepo.findByRoleOwnerAndId(Roles.ROLE_OWNER,id)
                                                .stream()
                                                .map(owner->{
                                                    Owners foundOwner =  owner.getOwners();
                                                    Owners.OwnersProfile ownersProfile = foundOwner != null ? new
                                                                Owners.OwnersProfile(foundOwner.getId(),
                                                                                    foundOwner.getPhoneNumber(),
                                                                                    foundOwner.getAlternatePhoneNumber(),
                                                                                    foundOwner.getAddress(),
                                                                                    foundOwner.getRegistrationDate()):
                                                                                   new  Owners.OwnersProfile(null,null,null,null,null);
                                                    return new User.getOwnerProfile(owner.getUsername(), owner.getEmail(), ownersProfile); 
                                                                                }).findFirst();
        if(ownerById.isPresent()){
            return Response.ResponseHandler("Owner found Successfully.", HttpStatus.OK, ownerById);
        }else{
             return Response.ResponseHandler("Owner not found.",HttpStatus.NOT_FOUND);
        }
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler("Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Receptionist

    public ResponseEntity<?> changeReceShift(Long id,ReceShiftStatus shift){
       try{
            System.out.println("hello there");
           Optional<Receptionist> rece = receptRepo.findReceFromUserId(id);
           
           if(!rece.isPresent()){
               throw new UserNotFoundException("User not found.");
            }
            
            Receptionist foundRece =  rece.get();
            
            foundRece.setShift(shift);
            receptRepo.save(foundRece);
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
        }catch(UserNotFoundException e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    // public ResponseEntity<?> getOwnersWithDogsRecord(){
        
    // }

    // make service to register vet and receptionist
}
