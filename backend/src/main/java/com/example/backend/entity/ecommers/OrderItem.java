package com.example.backend.entity.ecommers;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
    @JoinColumn(name = "products_skus_id", referencedColumnName = "id")
    private ProductsSkus productsSkus;

    private Long quantity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public record orderItems(
        Long id, 
        Long quantity,
        Product.productsCartRecord produCartRecord, 
        ProductsSkus.productProductsSkus productsSkus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
        ) {}
}
