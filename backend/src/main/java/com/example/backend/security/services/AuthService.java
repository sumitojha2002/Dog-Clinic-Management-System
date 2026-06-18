package com.example.backend.security.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.entity.Owners;
import com.example.backend.exception.UserAlreadyExistException;
import com.example.backend.repository.OwnerRepository;
import com.example.backend.response.Response;
import com.example.backend.security.dto.UserDTO;
import com.example.backend.security.entity.User;
import com.example.backend.security.entity.enums.Roles;
import com.example.backend.security.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OwnerRepository ownerRepo;
   

        @Transactional
        public ResponseEntity<?> addPetOwner(UserDTO userdto){
            Optional<User> findUserByEmail = userRepository.findByEmail(userdto.getEmail());
            Optional<User> findUserByUsername = userRepository.findByUsername(userdto.getUsername());
            
            if(findUserByUsername.isPresent()){
                throw new UserAlreadyExistException("Username already exists.");
            }
            
            if(findUserByEmail.isPresent()){
                throw new UserAlreadyExistException("Email already exists");
            }
            
            Date inputDate = new Date();
            LocalDate localDate =  LocalDate.ofInstant(inputDate.toInstant(), ZoneId.systemDefault());

            User user = new User();
            user.setEmail(userdto.getEmail());
            user.setPassword(passwordEncoder.encode(userdto.getPassword()));
            user.setUsername(userdto.getUsername());
            user.setRole(Roles.ROLE_OWNER);
            Owners owners = new Owners();
            owners.setUser(user);
            owners.setRegistrationDate(localDate);
            ownerRepo.save(owners);

            userRepository.save(user);
            
            return Response.ResponseHandler("Added user Successfully", HttpStatus.OK);
    }
}
