package com.example.backend.helper;

import java.util.Optional;

import com.example.backend.entity.Appointments;
import com.example.backend.entity.Dogs;
import com.example.backend.entity.Owners;
import com.example.backend.entity.Veterinarians;
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

    public static Appointments.Appointment getAllAppoimentProfile(Appointments appointments){

        if(appointments ==  null) return null;

        Dogs.DogInner dogInfo = Optional.ofNullable(appointments)
            .map(Appointments::getDogs)
            .map(dog-> new Dogs.DogInner(
                dog.getId(), 
                dog.getName(), 
                dog.getBreed(), 
                dog.getGender(), 
                dog.getColor()))
                .orElse(null);
             
        Owners.OwnersProfile owners = Optional.ofNullable(appointments)
                .map(Appointments::getOwners)
                .map(owner -> new Owners.OwnersProfile(
                    new User.userInfo(owner.getUser().getUsername(),owner.getUser().getEmail()), 
                    owner.getId(), 
                    owner.getPhoneNumber(), 
                    owner.getAlternatePhoneNumber(), 
                    owner.getAddress(), 
                    owner.getRegistrationDate()))
                    .orElse(null);
        
        Veterinarians.fetProfile vets = Optional.ofNullable(appointments)
            .map(Appointments::getVeterinarians)
            .map(vet-> new Veterinarians.fetProfile(new User.userInfo(
                vet.getUser().getUsername(), 
                vet.getUser().getEmail()), 
                vet.getId(), 
                vet.getLicenseNumber(), 
                vet.getSpecialization(), 
                vet.getYearsOfExperience()))
                .orElse(null);
        
        return new Appointments.Appointment(
            appointments.getId(),
            dogInfo,
            appointments.getReason(),
            vets,
            owners,
            appointments.getAppointmentDate(),
            appointments.getAppointmentTime()
        );
    }

    // public static Dogs.DogInfo getDogsInfo()
}
