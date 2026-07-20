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
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderDetails orderDetails;

    // Fixed: Changed from @ManyToMany to @ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    // Fixed: Changed from @ManyToMany to @ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_sku_id", referencedColumnName = "id")
    private ProductsSkus productsSkus;

    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
