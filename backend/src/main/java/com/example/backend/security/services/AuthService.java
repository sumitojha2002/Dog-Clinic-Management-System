package com.example.backend.security.services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.exception.UserAlreadyExistException;
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
        
            User user = new User();
            user.setEmail(userdto.getEmail());
            user.setPassword(passwordEncoder.encode(userdto.getPassword()));
            user.setUsername(userdto.getUsername());
            user.setRole(Roles.ROLE_OWNER);
            userRepository.save(user);
            
            return Response.ResponseHandler("Added user Successfully", HttpStatus.OK);
    }
}
