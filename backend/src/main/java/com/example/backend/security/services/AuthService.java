package com.example.backend.security.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.entity.Employee;
import com.example.backend.entity.Owners;
import com.example.backend.entity.Receptionist;
import com.example.backend.entity.Veterinarians;
import com.example.backend.entity.enums.EmpStatus;
import com.example.backend.exception.UserAlreadyExistException;
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.repository.OwnerRepository;
import com.example.backend.repository.ReceptionistRepository;
import com.example.backend.repository.VeterinarianRepository;
import com.example.backend.response.Response;
import com.example.backend.security.dto.UserDTO;
import com.example.backend.security.entity.User;
import com.example.backend.security.entity.enums.Roles;
import com.example.backend.security.repository.UserRepository;
import com.example.backend.services.AdminServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OwnerRepository ownerRepo;
    private final AdminServices adminServices;
    private final ReceptionistRepository receptRepo;
    private final VeterinarianRepository vetRepo;
    private final EmployeeRepository empRepo;

        @Transactional
        public ResponseEntity<?> addUser(UserDTO userdto,Roles role){
            
            Optional<User> findUserByEmail = userRepository.findByEmail(userdto.getEmail());
            Optional<User> findUserByUsername = userRepository.findByUsername(userdto.getUsername());
            
            if(findUserByEmail.isPresent()){
                throw new UserAlreadyExistException("Email already exists");
            }

            if(findUserByUsername.isPresent()){
                throw new UserAlreadyExistException("Username already exists.");
            }
            
            Date inputDate = new Date();
            LocalDate localDate =  LocalDate.ofInstant(inputDate.toInstant(), ZoneId.systemDefault());

            User user = new User();
            user.setEmail(userdto.getEmail());
            user.setPassword(passwordEncoder.encode(userdto.getPassword()));
            user.setUsername(userdto.getUsername());
            user.setRole(role);
            if(role.equals(Roles.ROLE_OWNER)){

                Owners owners = new Owners();
                owners.setUser(user);
                owners.setRegistrationDate(localDate);
                ownerRepo.save(owners);

            }else if(role.equals(Roles.ROLE_RECEP)){

                Receptionist receptionist = new Receptionist();
                receptionist.setUser(user);
                Employee emp = new Employee();
                emp.setHireDate(localDate);
                emp.setUser(user);
                emp.setStatus(EmpStatus.INACTIVE);
                receptionist.setEmployee(emp);
                empRepo.save(emp);
                receptRepo.save(receptionist);

            }else if(role.equals(Roles.ROLE_VET)){

                Veterinarians vet = new Veterinarians();
                Employee emp = new Employee();
                emp.setHireDate(localDate);
                emp.setUser(user);
                emp.setStatus(EmpStatus.INACTIVE);
                vet.setUser(user);
                vet.setEmployee(emp);
                empRepo.save(emp);
                vetRepo.save(vet);
                
            }

            userRepository.save(user);
            
            return Response.ResponseHandler("Added user Successfully", HttpStatus.OK);
    }
}
