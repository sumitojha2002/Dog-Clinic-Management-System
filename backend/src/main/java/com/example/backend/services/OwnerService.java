package com.example.backend.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.backend.entity.Appointments;
import com.example.backend.entity.Dogs;
import com.example.backend.entity.MedicalRecord;
import com.example.backend.entity.Owners;
import com.example.backend.entity.Veterinarians;
import com.example.backend.entity.dto.AppointmentDTO;
import com.example.backend.entity.dto.OwnerPetDTO;
import com.example.backend.entity.dto.OwnerProfileDTO;
import com.example.backend.entity.enums.AppointmentStatus;
import com.example.backend.entity.enums.VactionationStatus;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.helper.ProfileHelper;
import com.example.backend.repository.AppointmentRepository;
import com.example.backend.repository.DogsRepository;
import com.example.backend.repository.MedicalRecordRepository;
import com.example.backend.repository.OwnerRepository;
import com.example.backend.repository.VeterinarianRepository;
import com.example.backend.response.Response;
import com.example.backend.security.entity.User;
import com.example.backend.security.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepo;
    private final DogsRepository dogsRepo;
    private final UserRepository userRepo;
    private final AppointmentRepository appRepo;
    private final VeterinarianRepository vetRepo;
    private final MedicalRecordRepository medicalRecordRepo;

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
            List<Dogs.DogInner> listOfDogs = owner.getDogs()
                .stream()
                .map(ProfileHelper::getDogsInnerInfo)
                .toList();
            
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

    public ResponseEntity<?> getAllDogs(Owners owners){
        try{
            List<Dogs.DogInner> dogs  = owners
            .getDogs()
            .stream()
            .map(ProfileHelper::getDogsInnerInfo)
            .toList();
            if(dogs.isEmpty()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }
            return Response.ResponseHandler(HttpStatus.FOUND.getReasonPhrase(), HttpStatus.FOUND,dogs);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public ResponseEntity<?> addPet(OwnerPetDTO ownerPetdto,Owners owners){
        try{
            List<Dogs.DogInner> dogsList = owners.getDogs()
                .stream()
                .map(ProfileHelper::getDogsInnerInfo)
                .toList();

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

    // Owner Profile
    public ResponseEntity<?> getOwnerProfile(User user){
        try{
            Owners.OwnersProfile owner =  ownerRepo.findByUserId(user.getId())
                .stream()
                .map(u ->new Owners.OwnersProfile(new User.userInfo(user.getUsername(), user.getEmail()),
                u.getId(),
                u.getPhoneNumber(),
                u.getAlternatePhoneNumber(),
                u.getAddress(),
                u.getRegistrationDate())
        )
        .findFirst().get();
    
        return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,owner);
    }catch(Exception e){
        e.printStackTrace();
        return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    public List<LocalTime> getAppointmentTime(LocalDate localDate){
        List<LocalTime> allSlots = new ArrayList<>(List.of(
            LocalTime.of(9,0),
            LocalTime.of(9,30),
            LocalTime.of(10,0),
            LocalTime.of(10,30),
            LocalTime.of(11,0),
            LocalTime.of(11,30),
            LocalTime.of(13,0),
            LocalTime.of(13,30),
            LocalTime.of(14,0),
            LocalTime.of(14,30),
            LocalTime.of(15,0),
            LocalTime.of(15,30),
            LocalTime.of(16,0),
            LocalTime.of(16,30)
        ));

        List<LocalTime> removeTimeTiming  = appRepo.getAllAppointmentsTime(localDate).stream().map(ap-> ap.getAppointmentTime()).toList();
        if(removeTimeTiming.isEmpty()){
            return allSlots;
        }
        allSlots.removeAll(removeTimeTiming);
        return allSlots;
    }


    // Set Appoitment
    @Transactional
    public ResponseEntity<?> setAppoinmentForThePet(Long id, AppointmentDTO appDTO,UserDetails userDetails){
        try{


            List<Appointments> apps = appRepo.getAllAppointments().stream()
                .filter(ap-> ap.getAppointmentDate().equals(appDTO.getAppointmentDate()) & ap.getAppointmentTime().equals(appDTO.getAppointmentTime())).toList();

            if(!apps.isEmpty()){
                return Response.ResponseHandler("Try different date or time.", HttpStatus.CONFLICT);
            }

    
            Dogs dog = dogsRepo
                .findById(id)
                .orElseThrow(()-> new UserNotFoundException("Dog with this id not found.")); 

            Veterinarians vet = vetRepo
                .findById(appDTO.getVetId())
                .orElseThrow(()-> new UserNotFoundException("Veterinarian not found."));

            String username = userDetails.getUsername();

            User user = userRepo
                .findByUsernameOrEmail(username)
                .orElseThrow(()-> new UserNotFoundException("user not found."));

            Owners owner = ownerRepo
                .findByUserId(user.getId())
                .orElseThrow(()-> new UserNotFoundException("user not found."));
            
                
            if(!dog.getOwners().getId().equals(owner.getId())){
                return Response.ResponseHandler(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN);
            }
            
            boolean hasConflict = appRepo.findByDogId(id)
                .stream()
                .anyMatch(app -> app.getAppointmentDate().equals(appDTO.getAppointmentDate()));

            if(hasConflict){
                return Response.ResponseHandler("This dog has already an appointment with vet today.",HttpStatus.CONFLICT);
            }
            
            Appointments appointments = new Appointments();
            
            appointments.setAppointmentDate(appDTO.getAppointmentDate());
            appointments.setAppointmentTime(appDTO.getAppointmentTime());
            appointments.setDogs(dog);
            appointments.setReason(appDTO.getReason());
            appointments.setStatus(AppointmentStatus.PENDING);
            appointments.setOwners(owner);
            appointments.setVeterinarians(vet);
            appRepo.save(appointments);
            
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
             
        }catch(UserNotFoundException e){
            return Response.ResponseHandler(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get medical record
    public ResponseEntity<?> medicalRecord(Long id,UserDetails userDetails){
        try{
            User user = userRepo
            .findByUsernameOrEmail(userDetails
                .getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("User not found exception."));
                
                Owners owners = ownerRepo.findByUserId(user.getId())
                .orElseThrow(()-> new UserNotFoundException("Owners not found."));
                
                List<Dogs> dogs = owners.getDogs();
                
                boolean dogBelongsToOwner = dogs.stream()
                .anyMatch(d-> d.getId() == id);
                
                if(!dogBelongsToOwner){
                    return Response.ResponseHandler("The dog does not belog to the owner", HttpStatus.CONFLICT);
                }    
                
                List<MedicalRecord.medicalRecord> dog = medicalRecordRepo.findMedicalRecordById(id)
                .stream()
                .map(ProfileHelper::getMedicalRecord)
                .toList();
                return Response.ResponseHandler("All medical record of the dog", HttpStatus.OK,dog);
            }catch(UserNotFoundException e){
                e.printStackTrace();
                return Response.ResponseHandler(e.getMessage(), HttpStatus.NOT_FOUND);
            }catch(Exception e){
                e.printStackTrace();
                return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }
}
