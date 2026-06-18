package com.example.backend.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.backend.exception.UserNotFoundException;
import com.example.backend.security.dto.CustomUserDetials;
import com.example.backend.security.entity.User;
import com.example.backend.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetialsServices implements UserDetailsService{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsernameOrEmail(username).orElseThrow(()-> new UserNotFoundException("User not found"));

        return new CustomUserDetials(user);
    }
}
