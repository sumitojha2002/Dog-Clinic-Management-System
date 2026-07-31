package com.example.backend.controller.owner;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.services.ecommers.OrderServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/owner/orders")
@RequiredArgsConstructor
public class OwnerOrderController {
    private final OrderServices orderServices;

    @GetMapping
    public ResponseEntity<?> getAllOrders(@AuthenticationPrincipal UserDetails userDetails){
        return orderServices.getAllOrders(userDetails);
    }

    @PostMapping
    public ResponseEntity<?> postOrder(@AuthenticationPrincipal UserDetails userDetails){
        return orderServices.postAllItemsToOrderItems(userDetails);
    }

}
