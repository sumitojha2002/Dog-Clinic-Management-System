package com.example.backend.repository.ecommers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.ecommers.OrderDetails;


public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {
    
}
