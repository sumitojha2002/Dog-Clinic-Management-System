package com.example.backend.services;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.backend.entity.Employee;
import com.example.backend.entity.Receptionist;
import com.example.backend.entity.dto.ReceDTO;
import com.example.backend.entity.enums.EmpStatus;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.repository.ReceptionistRepository;
import com.example.backend.response.Response;
import com.example.backend.security.entity.User;
import com.example.backend.security.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceptionistService {
    private final ReceptionistRepository receRepo;
    private final UserServices userServices;
    private final UserRepository userRepo;


    public ResponseEntity<?> getReceProfile(String username){
        try{
            Optional<User.getReceProfile> user = userServices.findUser(username)
                                    .stream()
                                    .map(u->{
                                        Employee emp = u.getEmployee();
                                        Receptionist rece = u.getReceptionist();

                                        Receptionist.Rece receDetails = rece != null ?
                                                       new  Receptionist.Rece(rece.getShift()) :
                                                       new Receptionist.Rece(null);
                                        
                                        Employee.EmpRece empDetail =  emp != null ?
                                                       new Employee.EmpRece(emp.getPhoneNumber(),emp.getHireDate(), emp.getStatus(),receDetails):
                                                       new Employee.EmpRece(null, null, null, receDetails);

                                        return new User.getReceProfile(u.getUsername(),u.getEmail(),empDetail);
                                    }).findFirst();
                                   
        if(!user.isPresent()){
            throw new UserNotFoundException("User not found.");
        } 

        return Response.ResponseHandler("Profile found successfully.", HttpStatus.FOUND, user);
        
        }catch(UserNotFoundException e){
            return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> changeProfile(ReceDTO receDTO,String username){
        try{

            User user = userServices.findUser(username).orElseThrow(()-> new UserNotFoundException("User not found."));
            Employee emp = user.getEmployee();
            emp.setStatus(EmpStatus.ACTIVE);
            emp.setPhoneNumber(receDTO.getPhoneNumber());
            user.setEmployee(emp);
            userRepo.save(user);
            return Response.ResponseHandler("User has been updated successfully.", HttpStatus.ACCEPTED);
        }catch(UsernameNotFoundException e){
            return Response.ResponseHandler("User not found.", HttpStatus.NOT_FOUND);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
