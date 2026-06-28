package com.example.backend.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.backend.security.entity.User;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity 
public class Veterinarians {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;   

    @Column(unique = true)
    private String licenseNumber;

    @ElementCollection
    @CollectionTable(name="specialization", joinColumns = @JoinColumn(name="vet_id"))
    @Column(name = "specialization")
    private Set<String> specialization =  new HashSet<>();

    private Long yearsOfExperience;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="emp_id",referencedColumnName = "id")
    private Employee employee;

    @OneToMany(mappedBy = "veterinarians",fetch = FetchType.LAZY)
    private List<Appointments> appointment;

    public record vetProfile(User.userInfo user,Long vetId, String licenseNumber,Set<String> specialization, Long yearsOfExperience){}
    public record vet(Long vetId,String licenseNumber,Set<String> specialization,Long yearsOfExperience){}
}
