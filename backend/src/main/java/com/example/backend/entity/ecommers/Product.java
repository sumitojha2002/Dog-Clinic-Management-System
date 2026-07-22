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
    private String description;
    private String summery;
    
    private String cover;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id",referencedColumnName = "id")
    private SubCategory subCategory;

    @OneToMany(fetch=FetchType.LAZY , mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartItem> cartItems;


    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductsSkus> productsSkus; 

    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public record productsRecord(
        Long id,
        Long subCategoryId,
        String name,
        String description,
        String summery,
        String cover,
        LocalDateTime createdAt,
        LocalDateTime deletedAt){}

    public record productRecordSkus(
        Long id,
        Long subCategoryId,
        String name,
        String description,
        String summery,
        String cover,
        LocalDateTime createAt,
        LocalDateTime deleteAt,
        List<ProductsSkus.productProductsSkus> productsSkus){}
}
