package com.example.backend.services;

import com.example.backend.security.entity.User;

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
import com.example.backend.helper.ProfileHelper;
import com.example.backend.repository.DogsRepository;
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.repository.OwnerRepository;
import com.example.backend.repository.ReceptionistRepository;
import com.example.backend.response.Response;
import com.example.backend.security.repository.RefreshRepository;
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
    private final RefreshRepository refreshRepo;

    // owners

    @Transactional
    public ResponseEntity<?> deleteOwnerById(Long id) {
        try {
            Owners owner = ownerRepo.findByOwnerId(id)
                    .orElseThrow(() -> new UserNotFoundException("User does not exist"));

            Long userId = owner.getUser().getId();

            dogsRepo.deleteByOwnerId(owner.getId());
            refreshRepo.deleteByUserId(userId);

            ownerRepo.delete(owner); 
            
            userRepo.deleteById(userId);

            return Response.ResponseHandler("Owner of id has been successfully deleted.", HttpStatus.OK);

        } catch (UserNotFoundException e) {
            return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllOwners(){
        try{
            List<Owners.OwnersProfile> ownersProfiles = ownerRepo.findAllUser()
                                                .stream()
                                                .map(ProfileHelper::getAllOwnersProfile)
                                                .toList();
            if(ownersProfiles.isEmpty()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,ownersProfiles);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
    }

     public ResponseEntity<?> getOwnerById(Long id){
        try{
            Optional<Owners.OwnersProfile> ownerProfile = ownerRepo.findByOwnerId(id)
                .stream()
                .map(ProfileHelper::getAllOwnersProfile)
                .findFirst();
            if(ownerProfile.isPresent()){
                return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK, ownerProfile);
            }
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND, ownerProfile);                            
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler("Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // dogs

    public ResponseEntity<?> deleteByDogsId(Long id){
        try{
            Optional<Dogs> dog = dogsRepo.findById(id);
            
            if(dog.isPresent()){
                dogsRepo.deleteById(id);    
                return Response.ResponseHandler("Dog has been successfully deleted from the db.", HttpStatus.OK);
            }
            throw new UserNotFoundException("User not found exception.");
        }catch(UserNotFoundException e){
            return Response.ResponseHandler(
                HttpStatus.NOT_FOUND.getReasonPhrase(), 
                HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllDogsRecord(String name,String breed){
        try{
            String safeName = (name != null && !name.isEmpty()) ? name : null;
            String safeBreed = (breed != null && !breed.isEmpty()) ? breed: null;
            
            List<Dogs.DogInner> dogs = dogsRepo.findAllDogsByNameAndBreed(safeBreed,safeName)
                                                .stream()
                                                .map(ProfileHelper::getDogsInnerInfo)
                                                .toList();
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

    public ResponseEntity<?> getDogsInfoById(Long id){
        try{

            Optional<Dogs.DogInfo> profile = dogsRepo.findDogsFullDetails(id)
            .stream()
            .map(this::getDogAndOwnerInfo)
            .findFirst();
        
        if(profile.isPresent()){
            return Response.ResponseHandler(HttpStatus.FOUND.getReasonPhrase(), HttpStatus.FOUND,profile);
        }
        return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   
    public Dogs.DogInfo getDogAndOwnerInfo(Dogs dogs){
        Owners owner = dogs.getOwners();
        
        User.userInfo userInfo = Optional.ofNullable(owner)
            .map(Owners::getUser)
            .map(u-> new User.userInfo(u.getUsername(), u.getEmail()))
            .orElse(null);
        
        Owners.OwnersProfile ownersProfile = new Owners.OwnersProfile(userInfo, 
            owner.getId(),
            owner.getPhoneNumber() , 
            owner.getAlternatePhoneNumber(), 
            owner.getAddress(), 
            owner.getRegistrationDate());
        
        return new Dogs.DogInfo(
            dogs.getId(), 
            dogs.getName(), 
            dogs.getBreed(),
            dogs.getGender(), 
            dogs.getColor(),
            dogs.getWeight(),
            dogs.getDateOfBirth(),
            dogs.getVaccinationStatus(),
            dogs.getRegisteredDate(),
            dogs.getLastVisitDate(),
            dogs.getStatus(),
            ownersProfile);
    }

    // Emp

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

    @Transactional
    public ResponseEntity<?> deleteEmpByEmpId(Long id){
        try{
            Employee emp = empRepo.findById(id).orElseThrow(()-> new UserNotFoundException(""));
            User user = userRepo.findById(emp.getUser().getId()).get();
            refreshRepo.deleteByUserId(user.getId());
            empRepo.delete(emp);
            userRepo.deleteById(user.getId());
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
        }catch(UserNotFoundException e){
            return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Receptionist

    public ResponseEntity<?> changeReceShift(Long id,ReceShiftStatus shift){
       try{
           Optional<Receptionist> rece = receptRepo.findReceFromReceId(id);
           
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
}
