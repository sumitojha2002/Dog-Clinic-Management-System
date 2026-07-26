package com.example.backend.repository.ecommers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.ecommers.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("""
            SELECT c FROM Cart c
            LEFT JOIN FETCH c.cartItems
            WHERE c.user.id = :id
            """)
    Optional<Cart>  findByUserId(@Param("id") Long id);  
}
