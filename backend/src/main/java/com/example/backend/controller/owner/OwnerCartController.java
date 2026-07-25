package com.example.backend.controller.owner;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.controller.admin.PathVariabel;
import com.example.backend.entity.ecommers.dto.CartItemDTO;
import com.example.backend.entity.ecommers.dto.UpdateCartItemDTO;
import com.example.backend.services.ecommers.CartServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/owners/cart")
@RequiredArgsConstructor
public class OwnerCartController {
    private final CartServices cartServices;

    @GetMapping()
    public ResponseEntity<?> getAllItem(@AuthenticationPrincipal UserDetails userDetails){
        return cartServices.getAllCartItems(userDetails);
    }

    @PostMapping("/items")
    public ResponseEntity<?> addItem(@AuthenticationPrincipal UserDetails userDetails,@ModelAttribute CartItemDTO cartItemDTO){
        return cartServices.addItem(userDetails, cartItemDTO);
    }

    @PutMapping("/itmes/{cartItemId}")
    public ResponseEntity<?> updateCartItemInCart(@AuthenticationPrincipal UserDetails userDetails,@PathVariabel Long cartItemId,@ModelAttribute UpdateCartItemDTO cartItemDTO){
        return cartServices.updateCartItemInCart(userDetails,cartItemId,cartItemDTO);
    }

    @DeleteMapping("/items/{cartItemID}")
    public ResponseEntity<?> deleteCartItem(@AuthenticationPrincipal UserDetails userDetails, @PathVariabel Long cartItemID){
        return cartServices.deleteByCartItemId(userDetails,cartItemID);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCartItem(@AuthenticationPrincipal UserDetails userDetails){
        return cartServices.clearCart(userDetails);
    }
}
