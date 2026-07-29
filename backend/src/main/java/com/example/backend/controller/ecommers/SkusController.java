package com.example.backend.controller.ecommers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.services.ecommers.ProductsSkusServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/skus")
@RequiredArgsConstructor
public class SkusController {
    private final ProductsSkusServices productsSkusServices;

    @GetMapping("/{id}")
    public ResponseEntity<?> getSkusById(@PathVariable Long id){
        return productsSkusServices.getProductSkusById(id);
    }
}
