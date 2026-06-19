package com.example.backend.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.entity.Dogs;
import com.example.backend.entity.Owners;
import com.example.backend.entity.dto.OwnerPetDTO;
import com.example.backend.entity.dto.OwnerProfileDTO;
import com.example.backend.entity.enums.VactionationStatus;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.repository.DogsRepository;
import com.example.backend.repository.OwnerRepository;
import com.example.backend.response.Response;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepo;
    private final DogsRepository dogsRepo;

    @Transactional
    public ResponseEntity<?> updateOwnersProfile(OwnerProfileDTO ownersProfiledto, Owners owners){
        try{
            System.out.println(ownersProfiledto);
            owners.setAddress(ownersProfiledto.getAddress());
            owners.setAlternatePhoneNumber(ownersProfiledto.getAlternatePhoneNumber());
            owners.setPhoneNumber(ownersProfiledto.getPhoneNumber());
            System.out.println(owners.getUser().getUsername()+owners.getAddress()+owners.getPhoneNumber()+owners.getAlternatePhoneNumber());
            ownerRepo.save(owners);
            return Response.ResponseHandler("User profile has been successfully updated.", HttpStatus.OK);
        }catch(Exception e){

            return Response.ResponseHandler("Failed to update the Profile.", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> findPetsByID(Long id,Owners owner){
        try{

            List<Dogs.DogInner> listOfDogs = getAllDogs(owner);
            Optional<Dogs.DogInner> foundDog =  listOfDogs.stream().filter(dog-> dog.id().equals(id)).findFirst();
            
            if(foundDog.isPresent()){
                Dogs.DogInner resDog = foundDog.get();
                return Response.ResponseHandler("The dog with id found successfully.", HttpStatus.OK, resDog);
            }else{
                throw new UserNotFoundException("Dog not found. May not be users dogs id.");
            }
            }
        catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler("Dog not found. May not be users dogs id.", HttpStatus.NOT_FOUND);
        }
    }

    public Owners findOwnerById(long id){
        return ownerRepo.findByUserId(id).orElseThrow(()->new UserNotFoundException("Owner not found"));
    }

    public List<Dogs.DogInner> getAllDogs(Owners owner){
        return dogsRepo.findAllDogs(owner.getId())
                                    .stream()
                                    .map(dogs-> new Dogs.DogInner(
                                        dogs.getId(),
                                        dogs.getName(), 
                                        dogs.getBreed(), 
                                        dogs.getGender(), 
                                        dogs.getColor())).toList();
    }

    @Transactional
    public ResponseEntity<?> addPet(OwnerPetDTO ownerPetdto,Owners owners){
        try{
            List<Dogs.DogInner> dogsList = getAllDogs(owners);

            Optional<Dogs.DogInner> foundDog = dogsList.stream()
                                                        .filter(dog->
                                                            dog.name().matches(ownerPetdto.getName()))
                                                            .findFirst();
            if(foundDog.isPresent()){
                return Response.ResponseHandler("Cannot have pet dog with same name.", HttpStatus.NOT_ACCEPTABLE);
            }

            Date inputDate = new Date();
            
            LocalDate localDate = LocalDate.ofInstant(inputDate.toInstant(), ZoneId.systemDefault());
            
            Dogs dogs = new Dogs();
            
            
            owners.getDogs().add(dogs);
            dogs.setName(ownerPetdto.getName());
            dogs.setBreed(ownerPetdto.getBreed());
            dogs.setGender(ownerPetdto.getGender());
            dogs.setColor(ownerPetdto.getColor());
            dogs.setDateOfBirth(ownerPetdto.getDateOfBirth());
            dogs.setRegisteredDate(localDate);
            dogs.setOwners(owners);
            dogs.setVaccinationStatus(VactionationStatus.UNKNOWN);

            dogsRepo.save(dogs);
            return Response.ResponseHandler("Your pet has successfully registered.", HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler("Failed to register your pet", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
