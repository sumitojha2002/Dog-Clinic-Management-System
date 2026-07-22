package com.example.backend.entity.ecommers;

import java.time.LocalDateTime;

import com.example.backend.entity.ecommers.enums.ProductAttributesType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProductAttributes {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;    

    private ProductAttributesType productAttributesType;

    private String value;

    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public record productAttr(
        Long id, 
        ProductAttributesType productAttributesType, 
        String value,
        LocalDateTime createdAt,
        LocalDateTime deletedAt){}
}
