package com.example.backend.entity.ecommers;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String desc;
    private String summery;
    
    private String cover;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id",referencedColumnName = "id")
    private SubCategory subCategory;

    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}
