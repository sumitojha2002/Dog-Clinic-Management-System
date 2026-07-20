package com.example.backend.entity.ecommers;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}
