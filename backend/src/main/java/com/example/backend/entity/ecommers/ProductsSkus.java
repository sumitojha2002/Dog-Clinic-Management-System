package com.example.backend.entity.ecommers;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name ="products_skus")
public class ProductsSkus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id",referencedColumnName = "id")
    private Product productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="size_attribute_id",referencedColumnName = "id")
    private ProductAttributes sizeAttributeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="color_attribute_id",referencedColumnName = "id")
    private ProductAttributes colorAttributeId;

    private String sku;

    private Double price;

    private Long quantity;

    private LocalDateTime createdAt;
    private LocalDateTime deleteAt;
}
