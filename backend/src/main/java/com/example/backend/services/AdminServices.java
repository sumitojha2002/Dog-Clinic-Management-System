package com.example.backend.services;

import com.example.backend.security.entity.User;
import com.example.backend.security.entity.enums.Roles;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.entity.Dogs;
import com.example.backend.entity.Employee;
import com.example.backend.entity.Owners;
import com.example.backend.entity.Receptionist;
import com.example.backend.entity.Veterinarians;
import com.example.backend.entity.enums.ReceShiftStatus;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.repository.DogsRepository;
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.repository.OwnerRepository;
import com.example.backend.repository.ReceptionistRepository;
import com.example.backend.response.Response;
import com.example.backend.security.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServices {
    private final UserRepository userRepo;
    private final OwnerRepository ownerRepo;
    private final DogsRepository dogsRepo;
    private final ReceptionistRepository receptRepo;
    private final EmployeeRepository empRepo;

    @Transactional
    public boolean deleteById(Long id){
        try{
            userRepo.deleteById(id);
            return true;
        }catch(Exception e){
            throw e;
        }
    }

    // owners
    
    public ResponseEntity<?> getAllOwners(){
        try{
            List<Owners.OwnersProfile> ownersProfiles =ownerRepo.findAllUser()
                                                .stream()
                                                .map(this::getAllOwnersProfile)
                                                .toList();
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,ownersProfiles);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }

    }

     public ResponseEntity<?> getOwnerById(Long id){
        try{
            Optional<Owners.OwnersProfile> ownerProfile = ownerRepo.findById(id)
                .stream()
                .map(this::getAllOwnersProfile)
                .findFirst();
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK, ownerProfile);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler("Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Owners.OwnersProfile getAllOwnersProfile(Owners owner){
        
        User.userInfo userProfile = Optional.ofNullable(owner)
            .map(Owners::getUser)
            .map(u -> new User.userInfo(u.getUsername(), u.getEmail()))
            .orElse(null);

        return new Owners.OwnersProfile(
            userProfile, 
            owner.getId(), 
            owner.getPhoneNumber(),
            owner.getAlternatePhoneNumber() ,
            owner.getAddress(), 
            owner.getRegistrationDate());
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


   

    // Emplo

    public ResponseEntity<?> getAllEmpDetails(){
        try{
            List<Employee.Emp> emps = empRepo.findAllEmployees()
                .stream()
                .map(this::toEmpProfile)
                .toList();
            
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,emps);
        }catch(Exception e){
            log.error("Failed to fetch employee details.", e);
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Employee.Emp toEmpProfile(Employee employee){

        Receptionist.Rece rece = Optional.ofNullable(employee)
            .map(Employee::getReceptionist)
            .map(r->new Receptionist.Rece(r.getShift()))
            .orElse(null);
        
        Veterinarians.vet vet = Optional.ofNullable(employee)
            .map(Employee::getVeterinarians)
            .map(v->new Veterinarians.vet(v.getId(), v.getLicenseNumber(), v.getSpecialization(), v.getYearsOfExperience()))
            .orElse(null);
        
        User.userInfo user = Optional.ofNullable(employee)
            .map(Employee::getUser)
            .map(u-> new User.userInfo(u.getUsername(), u.getEmail()))
            .orElse(null);

        return new Employee.Emp(employee.getPhoneNumber(),
             employee.getHireDate(), 
             employee.getStatus(), 
             user, 
             rece, 
             vet);
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
