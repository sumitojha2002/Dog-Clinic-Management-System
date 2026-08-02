package com.example.backend.entity.ecommers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.backend.security.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;

    private Double total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "orderDetails")
    private List<OrderItem> orderItem = new ArrayList<>();

    public record orderDetailsListInfo(
        Long id,
        Double total,
        LocalDateTime createdAt,
        LocalDateTime updatedAt){}

    public record orderDetailsByUserId(
        Long id,
        Double total,
        List<OrderItem.orderItems> orderItems,
        LocalDateTime createdAt,
        LocalDateTime updatedAt){}
}
