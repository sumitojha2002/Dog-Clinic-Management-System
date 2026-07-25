package com.example.backend.repository.ecommers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.ecommers.Cart;
import com.example.backend.entity.ecommers.CartItem;
import com.example.backend.entity.ecommers.ProductsSkus;

public interface CartItemRepository extends JpaRepository<CartItem,Long>{
        @Query("""
            SELECT c FROM Cart c
            LEFT JOIN FETCH c.product
            WHERE c.cart.id =:id
            """)
    List<CartItem> findCartItemsByCartId(@Param("id")Long id);

        @Query("""
            SELECT c FROM Cart c
            LEFT JOIN FETCH c.product
            WHERE c.cart.id =:id
            """)
    Optional<CartItem> findCartItemByCartId(@Param("id")Long id);
        
    Optional<CartItem> findByCartAndProductsSkus(Cart cart, ProductsSkus sku);
    void deleteByCart(Cart cart);
}
