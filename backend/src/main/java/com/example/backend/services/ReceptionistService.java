package com.example.backend.services;

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
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.response.Response;
import com.example.backend.security.entity.User;
import com.example.backend.security.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceptionistService {
    private final UserServices userServices;
    private final UserRepository userRepo;
    private final EmployeeRepository empRepo;


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
}
