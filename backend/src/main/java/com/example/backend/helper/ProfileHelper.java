package com.example.backend.helper;

import java.util.Optional;

import com.example.backend.entity.Appointments;
import com.example.backend.entity.Dogs;
import com.example.backend.entity.MedicalRecord;
import com.example.backend.entity.Owners;
import com.example.backend.entity.Veterinarians;
import com.example.backend.security.entity.User;

public class ProfileHelper {

    public static Dogs.DogCardInfo getDogCardInfo(Dogs dog){
        if(dog == null) return null;
        return new Dogs.DogCardInfo(
            dog.getId(),
            dog.getImageUrl(),
            dog.getName(), 
            dog.getDateOfBirth(), 
            dog.getBreed());
    }

    public static Dogs.DogInner getDogsInnerInfo(Dogs dog){
        if(dog == null) return null;
        
        return new Dogs.DogInner(
                dog.getId(), 
                dog.getName(), 
                dog.getImageUrl(),
                dog.getBreed(), 
                dog.getGender(), 
                dog.getColor(),
                dog.getWeight(),
                dog.getDateOfBirth(),
                dog.getVaccinationStatus(),
                dog.getAllergies(),
                dog.getChronicConditions(),
                dog.getRegisteredDate(),
                dog.getLastVisitDate(),
                dog.getStatus());
    }

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
                dog.getImageUrl(),
                dog.getBreed(), 
                dog.getGender(), 
                dog.getColor(),
                dog.getWeight(),
                dog.getDateOfBirth(),
                dog.getVaccinationStatus(),
                dog.getAllergies(),
                dog.getChronicConditions(),
                dog.getRegisteredDate(),
                dog.getLastVisitDate(),
                dog.getStatus()))
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

    public static MedicalRecord.medicalRecord getMedicalRecord(MedicalRecord medicalRecord){
        
        if(medicalRecord == null){
            return null;
        }

        Dogs.DogMedicalRecord dogMedicalRecord = Optional.ofNullable(medicalRecord)
            .map(MedicalRecord::getDogs)
            .map(dogs-> new Dogs.DogMedicalRecord(
                dogs.getId(), 
                dogs.getName(), 
                dogs.getBreed(), 
                dogs.getGender(), 
                dogs.getColor(), 
                dogs.getWeight()))
            .orElse(null);

        return new MedicalRecord.medicalRecord(
            medicalRecord.getId(),
            medicalRecord.getDiagnosis(),
            medicalRecord.getTreatment(),
            medicalRecord.getSymptoms(),
            medicalRecord.getNotes(),
            dogMedicalRecord);

    }

    // public static Dogs.DogInfo getDogsInfo()
}
