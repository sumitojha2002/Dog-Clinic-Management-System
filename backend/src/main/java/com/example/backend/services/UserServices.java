package com.example.backend.services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.entity.Owners;
import com.example.backend.exception.UserNotFoundException;
import com.example.backend.response.Response;
import com.example.backend.security.entity.User;
import com.example.backend.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServices {
    private final UserRepository userRepository;

    public ResponseEntity<?> getUserProfile(User user){
        Optional<User.getOwnerProfile> foundUser = userRepository.findById(user.getId())
                                                 .stream()
                                                 .map(u -> new User
                                                    .getOwnerProfile(
                                                        user.getUsername(), 
                                                        user.getEmail(),
                                                        new Owners.OwnersProfile(user.getOwners().getId(),
                                                                                user.getOwners().getPhoneNumber(),
                                                                                 user.getOwners().getAlternatePhoneNumber(),
                                                                                 user.getOwners().getAddress(),
                                                                                  user.getOwners().getRegistrationDate()))).findFirst();
        if(foundUser.isPresent()){
            return Response.ResponseHandler("User Profile.", HttpStatus.FOUND, foundUser);
        }else{
            throw new UserNotFoundException("We could not find the user.");
        }

    }
    public Optional<User> findUser(String username){
        return userRepository.findByUsernameOrEmail(username);
    }

}
