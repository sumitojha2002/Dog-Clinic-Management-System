package com.example.backend.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.backend.security.entity.User;
import com.example.backend.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServices {
    private final UserRepository userRepository;


    public Optional<User> findUser(String username){
        return userRepository.findByUsernameOrEmail(username);
    }

}
