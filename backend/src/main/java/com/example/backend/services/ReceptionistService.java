package com.example.backend.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.backend.entity.Appointments;
import com.example.backend.entity.Dogs;
import com.example.backend.entity.Employee;
import com.example.backend.entity.Owners;
import com.example.backend.entity.Receptionist;
import com.example.backend.entity.dto.ReceDTO;
import com.example.backend.entity.enums.AppointmentStatus;
import com.example.backend.entity.enums.EmpStatus;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.helper.ProfileHelper;
import com.example.backend.repository.AppointmentRepository;
import com.example.backend.repository.DogsRepository;
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.repository.OwnerRepository;
import com.example.backend.response.Response;
import com.example.backend.security.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceptionistService {

    private final EmployeeRepository empRepo;
    private final OwnerRepository ownerRepo;
    private final AppointmentRepository appRepo;
    private final DogsRepository dogRepo;
    // Receptionist

    public ResponseEntity<?> getReceProfile(Long id){
        try{
           Optional<Employee.EmpRece> emp =  empRepo.findReceProfile(id)
                .stream()
                .map(this::getEmpReceProfile)
                .findFirst();
        
        if(!emp.isPresent()){
            throw new UserNotFoundException("User not found.");
        }
        return Response.ResponseHandler("Profile found successfully.", HttpStatus.FOUND, emp);
        
        }catch(UserNotFoundException e){
            return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public Employee.EmpRece getEmpReceProfile(Employee emp){
        User.userInfo userInfo = Optional.ofNullable(emp)
            .map(Employee::getUser)
            .map(u->new User.userInfo(u.getUsername(), u.getEmail()))
            .orElse(null);
        
        Receptionist.Rece rece =  Optional.ofNullable(emp)
            .map(Employee::getReceptionist)
            .map(r->new Receptionist.Rece(r.getShift()))
            .orElse(null);
        
        return new Employee.EmpRece(userInfo, 
            emp.getPhoneNumber(), 
            emp.getHireDate(), 
            emp.getStatus(), 
            rece);
    }
    @Transactional
    public ResponseEntity<?> changeProfile(ReceDTO receDTO,Long id){
        try{
            Employee emp = empRepo.findReceProfile(id).getFirst();
            emp.setStatus(EmpStatus.ACTIVE);
            emp.setPhoneNumber(receDTO.getPhoneNumber());
            return Response.ResponseHandler("User has been updated successfully.", HttpStatus.ACCEPTED);
        }catch(UsernameNotFoundException e){
            return Response.ResponseHandler("User not found.", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get owner profile
    public ResponseEntity<?> getOwnersProfiles(){
        try{
            List<Owners.OwnersProfile> owners = ownerRepo.findAllUser()
                                                    .stream()
                                                    .map(ProfileHelper::getAllOwnersProfile).toList();
            if(owners.isEmpty()){
                throw new UserNotFoundException("User not found exception.");
            }
            return Response.ResponseHandler(HttpStatus.FOUND.getReasonPhrase(), HttpStatus.FOUND, owners);
        }catch(UserNotFoundException e)
        {
            return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        public ResponseEntity<?> getOwnersProfilesbyId(Long id){
        try{
            List<Owners.OwnersProfile> owners = ownerRepo.findByUserId(id)
                                                    .stream()
                                                    .map(ProfileHelper::getAllOwnersProfile).toList();
            if(owners.isEmpty()){
                throw new UserNotFoundException("User not found exception.");
            }
            return Response.ResponseHandler(HttpStatus.FOUND.getReasonPhrase(), HttpStatus.FOUND, owners);
        }catch(UserNotFoundException e)
        {
            return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // dogs
    // public ResponseEntity<?> getAllDogsInfo(String name,String breed){
    //     String safeString = (name != null && !name.isEmpty()) ? name : null;
    //     String safeBreed  = (breed != null && !breed.isEmpty()) ? breed: null;


    // }

    // Appointments
    public ResponseEntity<?> getAppointments(String status,LocalDate date){
        try{
            List<Appointments.Appointment> appo = appRepo.findAllthAppointments(status,date)
                .stream()
                .map(ProfileHelper::getAllAppoimentProfile)
                .toList();

            if(appo.isEmpty()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }
            return Response.ResponseHandler(HttpStatus.FOUND.getReasonPhrase(), HttpStatus.FOUND, appo);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Transactional
    public ResponseEntity<?> changeAppConfirm(Long id){
        try{
            Optional<Appointments> appointment = appRepo.findByAppID(id);

        
            if(appointment.isPresent()){
                if(appointment.get().getStatus().equals(AppointmentStatus.PENDING)){
                    appointment.get().setStatus(AppointmentStatus.CONFIRMED);
                    appRepo.save(appointment.get());
                }else if(appointment.get().getStatus().equals(AppointmentStatus.CONFIRMED)){
                    return Response.ResponseHandler("Already confirmed in.", HttpStatus.CONFLICT);
                }
            }
            
            return Response.ResponseHandler("Status changed to confirmed successfully.", HttpStatus.OK);
        }catch(UserNotFoundException e){
            return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> changeAppCheckIn(Long id){
        try{
            Optional<Appointments> appointment = appRepo.findByAppID(id);
            
            Date inputDate = new Date();
            
            LocalDate localDate = LocalDate.ofInstant(inputDate.toInstant(), ZoneId.systemDefault());

            if(appointment.isPresent() && appointment.get().getAppointmentDate().isEqual(localDate)){
                if(appointment.get().getStatus().equals(AppointmentStatus.CONFIRMED)){
                    appointment.get().setStatus(AppointmentStatus.CHECKED_IN);
                    Dogs dog = appointment.get().getDogs();
                    dog.setLastVisitDate(localDate);
                    dogRepo.save(dog);
                    appRepo.save(appointment.get());
                }else if(appointment.get().getStatus().equals(AppointmentStatus.CONFIRMED)){
                    return Response.ResponseHandler("Already confirmed.", HttpStatus.CONFLICT);
                }else{
                    return Response.ResponseHandler("Cannot change pending to checked in.", HttpStatus.CONFLICT);
                }
            }else{
                return Response.ResponseHandler("Status cannot be changed to confirmed not matching with appointment date.", HttpStatus.CONFLICT);
            }
            return Response.ResponseHandler("Status changed to checked in successfully.", HttpStatus.OK);
        }catch(UserNotFoundException e){
            return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
