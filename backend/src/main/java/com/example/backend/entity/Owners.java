package com.example.backend.entity;

import java.time.LocalDate;
import java.util.List;

import com.example.backend.security.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name="pet_owner")
public class Owners {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;

    @Column(name="phone_number",unique = true)
    private String phoneNumber;

    @Column(name="alt_phone_number",unique = true)
    private String alternatePhoneNumber;

    private String address;

    private LocalDate registrationDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "owners",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Dogs> dogs;

    public record OwnersDog(Dogs.DogInner dogs) {}

    public record OwnersProfile(User.userInfo user,
                                Long ownerId,
                                String phoneNumber,
                                String alternatePhoneNumber,
                                String address,
                                LocalDate registrationDate){}

}
