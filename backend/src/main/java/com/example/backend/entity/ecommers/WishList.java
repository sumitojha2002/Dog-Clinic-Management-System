package com.example.backend.entity.ecommers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.example.backend.security.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class WishList { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "wishlist_products", 
        joinColumns = @JoinColumn(name = "wishlist_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();
    
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public record usersWishList(Long id, List<Product.productsCartRecord> products) { }
}
