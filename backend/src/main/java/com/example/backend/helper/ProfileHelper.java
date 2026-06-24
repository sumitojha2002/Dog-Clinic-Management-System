package com.example.backend.helper;

import java.util.Optional;

import com.example.backend.entity.Dogs;
import com.example.backend.entity.Owners;
import com.example.backend.security.entity.User;

public class ProfileHelper {

    public static Owners.OwnersProfile getAllOwnersProfile(Owners owner){

        if (owner == null) {
        return null; 
        }
        
        User.userInfo userProfile = Optional.ofNullable(owner)
            .map(Owners::getUser)
            .map(u -> new User.userInfo(u.getUsername(), u.getEmail()))
            .orElse(null);

        return new Owners.OwnersProfile(
            userProfile, 
            owner.getId(), 
            owner.getPhoneNumber(),
            owner.getAlternatePhoneNumber() ,
            owner.getAddress(), 
            owner.getRegistrationDate());
    }

    // public static Dogs.DogInfo getDogsInfo()
}
