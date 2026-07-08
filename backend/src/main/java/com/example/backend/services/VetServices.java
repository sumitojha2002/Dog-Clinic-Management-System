package com.example.backend.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.example.backend.entity.Appointments;
import com.example.backend.entity.Dogs;
import com.example.backend.entity.MedicalRecord;
import com.example.backend.entity.Veterinarians;
import com.example.backend.entity.dto.DogsVetDTO;
import com.example.backend.entity.dto.MedicalRecordDTO;
import com.example.backend.entity.dto.VetDTO;
import com.example.backend.entity.enums.AppointmentStatus;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.helper.CheckHelper;
import com.example.backend.helper.ProfileHelper;
import com.example.backend.repository.AppointmentRepository;
import com.example.backend.repository.DogsRepository;
import com.example.backend.repository.MedicalRecordRepository;
import com.example.backend.repository.VeterinarianRepository;
import com.example.backend.response.Response;
import com.example.backend.security.entity.User;
import com.example.backend.security.repository.UserRepository;
import com.example.backend.security.services.CloudinaryServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VetServices {
    private final VeterinarianRepository vetRepo;
    private final UserRepository userRepo;
    private final AppointmentRepository appRepo;
    private final DogsRepository dogRepo;
    private final MedicalRecordRepository medicalRecordRepo;
    private final CloudinaryServices cloudService;
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

            String mimeType = vetDTO.getImage().getContentType();

            if(mimeType == null || !CheckHelper.filesType.contains(mimeType)){
               return Response.ResponseHandler("Invalid image type.", HttpStatus.FORBIDDEN);
            }
            String imageUrl = cloudService.upload(vetDTO.getImage());
            vet.setImageURL(imageUrl);
            vet.getSpecialization().addAll(vetDTO.getSpecialization());
            vet.setYearsOfExperience(vetDTO.getYearsOfExperince());
            
            vetRepo.save(vet);

            return Response.ResponseHandler(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED);
        }catch(UserNotFoundException e){
            e.printStackTrace();
            return Response.ResponseHandler(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(MaxUploadSizeExceededException e){
            e.printStackTrace();
            return Response.ResponseHandler("File size too big should be 10MB", HttpStatus.FORBIDDEN);    
        
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // getVetProfile for home page
    public ResponseEntity<?> getVetProfileForMain(int PageNum,int PageSize){
        try{
            Page<Veterinarians> vetPage = vetRepo.findVetforHomePage(PageRequest.of(PageNum-1, PageSize));
            
            List<Veterinarians.vetCard> vetList = vetPage
                .getContent()
                .stream()
                .map(ProfileHelper::getVetCardProfile)
                .toList();

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,vetList);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),HttpStatus.INTERNAL_SERVER_ERROR);
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


    // List of appoinment acc to the appointed vet 

    public ResponseEntity<?> getTheAppoinementAccToVetId(UserDetails userDetails){
        try{
            User user = userRepo
                .findByUsernameOrEmail(userDetails.getUsername())
                .orElseThrow(()-> new UserNotFoundException("User Not found."));

            Veterinarians vet = vetRepo
                .findVetByUserId(user.getId())
                .orElseThrow(()-> new UserNotFoundException("Vet not found."));
            
            List<Appointments.Appointment> vetAppoinement = appRepo.findAllthAppointmentsOfVetId(vet.getId(),AppointmentStatus.PENDING)
                .stream()
                .map(ProfileHelper::getAllAppoimentProfile)
                .toList();
            
            if(vetAppoinement.isEmpty()){
                return Response.ResponseHandler("No appointments made for "+ user.getUsername(), HttpStatus.NOT_FOUND);
            }
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK, vetAppoinement);
        }catch(UserNotFoundException e){
            e.printStackTrace();
            return Response.ResponseHandler(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();

            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // update to the appointment Status to IN_PROGRESS
    public ResponseEntity<?> appointmentStatusToInProgress(Long id, UserDetails userDetails){
        try{
            User user = userRepo
                .findByUsernameOrEmail(userDetails
                .getUsername())
                .orElseThrow(()-> new UserNotFoundException("User not found."));
            
            Veterinarians vet = vetRepo
                .findByUserId(user.getId())
                .orElseThrow(()->new UserNotFoundException("Veterinarian not found."));

             Optional<Appointments> vetAppoinement = appRepo.findAllthAppointmentsOfVetId(vet.getId(),AppointmentStatus.PENDING)
                .stream()
                .filter(app-> app.getId() == id).findFirst();
            
            if(!vetAppoinement.isPresent()){
                return Response.ResponseHandler("Appointment does not exist.", HttpStatus.NOT_FOUND);
            }

            Appointments appointment = vetAppoinement.get();
            if(!appointment.getStatus().equals(AppointmentStatus.CHECKED_IN)){
                return Response.ResponseHandler("Cannot set an appointment to IN_PROGRESS. Receptioninst must CHECK_IN first.", HttpStatus.CONFLICT);
            }
            appointment.setStatus(AppointmentStatus.IN_PROGRESS);
            appRepo.save(appointment);
            return Response.ResponseHandler("Appointment in progress.", HttpStatus.OK);

        }catch(UserNotFoundException e){
            e.printStackTrace();
            return Response.ResponseHandler(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // medical record
    @Transactional
    public ResponseEntity<?> createMedicalRecord(Long id,MedicalRecordDTO medicalRecordDTO,UserDetails userDetails){
        try{
            User user = userRepo.findByUsernameOrEmail(userDetails.getUsername())
                .orElseThrow(()-> new UserNotFoundException("User not found."));

            
            
            Veterinarians vet = vetRepo.findByUserId(user.getId())
                .orElseThrow(()-> new UserNotFoundException("Veterinarian not found."));
            
            Optional<Appointments> vetAppoinement = appRepo.findAllthAppointmentsOfVetId(vet.getId(),AppointmentStatus.PENDING)
                .stream()
                .filter(app-> app.getId() == id).findFirst();
            
            if(!vetAppoinement.isPresent()){
                return Response.ResponseHandler("Appointment does not exist.", HttpStatus.NOT_FOUND);
            }

            Appointments appointment = vetAppoinement.get();
            if(!appointment.getStatus().equals(AppointmentStatus.IN_PROGRESS)){
                return Response.ResponseHandler("Cannot create medical record. Needs to be set IN_PROGRESS", HttpStatus.CONFLICT);
            }
            Date inputDate = new Date();
            LocalDate localDate = LocalDate.ofInstant(inputDate.toInstant(), ZoneId.systemDefault());

            Dogs dog = appointment.getDogs();

            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setAppointments(appointment);
            medicalRecord.setDogs(dog);
            medicalRecord.setVeterinarians(vet);
            medicalRecord.setNotes(medicalRecordDTO.getNotes());
            medicalRecord.setSymptoms(medicalRecordDTO.getSymptoms());
            medicalRecord.setTreatment(medicalRecordDTO.getTreatment());
            medicalRecord.setDiagnosis(medicalRecordDTO.getDiagnosis());
            medicalRecord.setVisitDate(localDate);

            medicalRecordRepo.save(medicalRecord);
            return Response.ResponseHandler("Successfully saved medical record.", HttpStatus.OK);
        }catch(UserNotFoundException e){
            e.printStackTrace();
            return Response.ResponseHandler(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // dog fill in the dog profile.
    public ResponseEntity<?> setUpDogsProfile(DogsVetDTO dogsVetDTO,Long dogId,UserDetails userDetails){
        try{
            User user = userRepo.findByUsernameOrEmail(userDetails
                .getUsername())
                .orElseThrow(()->new UserNotFoundException("User not found."));
            
            Veterinarians vet = vetRepo.findByUserId(user.getId())
                .orElseThrow(()-> new UserNotFoundException("Veterinarian not found."));

            Appointments appointment =appRepo.findByDogAndVetId(dogId, vet.getId()).getFirst();

            if(appointment == null){
                return Response.ResponseHandler("Dog profile cannot be set by the vet. As vet has not been appointed.", HttpStatus.CONFLICT);
            }
            
            Dogs dog = appointment.getDogs();

            dog.getAllergies().addAll(dogsVetDTO.getAllergies());
            dog.getChronicConditions().addAll(dogsVetDTO.getChronicConditions());
            dog.setWeight(dogsVetDTO.getWeight());
            dog.setSpecialNotes(dogsVetDTO.getSpecialNotes());
            dogRepo.save(dog);
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(),HttpStatus.OK);
        }catch(UserNotFoundException e){
            e.printStackTrace();
            return Response.ResponseHandler(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
