package com.example.backend.repository.ecommers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.entity.ecommers.WishList;

public interface WishListItemRepository extends JpaRepository<WishList,Long> {
    
    @Query("""
            SELECT w FROM WishList w
            LEFT JOIN FETCH w.products
            WHERE w.user.id =:id
            """)
    Optional<WishList> findByUserIdWithProducts(@Param("id") Long id);

    @Query("""
            SELECT w FROM WishList w
            WHERE w.user.id =:id
            """)
    Optional<WishList> findByUserId(@Param("id") Long id);
}
