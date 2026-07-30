package com.example.backend.controller.owner;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.services.ecommers.WishListService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/owner/wishlist")
@RequiredArgsConstructor
public class OwnerWishListController {
    private final WishListService wishListService;

    @GetMapping
    public ResponseEntity<?> getUsersWishList(@AuthenticationPrincipal UserDetails userDetails){
        return wishListService.getAllWishList(userDetails);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<?> postProdcutInWishList(@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long productId){
        return wishListService.addProductToWishList(userDetails, productId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delByIdWishList(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){
        return wishListService.delByIdWishList(userDetails,id);
    }

}
