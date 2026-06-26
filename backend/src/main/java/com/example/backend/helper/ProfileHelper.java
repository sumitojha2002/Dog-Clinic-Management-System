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
        
        Veterinarians.vetProfile vets = Optional.ofNullable(appointments)
            .map(Appointments::getVeterinarians)
            .map(vet-> new Veterinarians.vetProfile(new User.userInfo(
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
            appointments.getStatus(),
            vets,
            owners,
            appointments.getAppointmentDate(),
            appointments.getAppointmentTime()
        );
    }

    public static Veterinarians.vetProfile getVetProfile (Veterinarians veterinarians){
        
        if(veterinarians == null){
            return null;
        }

        User.userInfo userInfo = Optional.ofNullable(veterinarians)
            .map(Veterinarians::getUser)
            .map(user-> new User.userInfo(user.getUsername(), user.getEmail()))
            .orElse(null);

        return new Veterinarians.vetProfile(userInfo,
            veterinarians.getId(),
            veterinarians.getLicenseNumber(), 
            veterinarians.getSpecialization(),
            veterinarians.getYearsOfExperience());
    }

    // public static Dogs.DogInfo getDogsInfo()
}
