package com.example.backend.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.ecommers.dto.UpdatePorductSkusDTO;
import com.example.backend.services.ecommers.ProductsSkusServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/admin/skus")
@RestController
public class AdminProductSkusController {
    private final ProductsSkusServices productsSkusServices;

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProductSkus(@ModelAttribute UpdatePorductSkusDTO updatePorductSkusDTO,@PathVariable Long id){
        return productsSkusServices.updateProductSkus(updatePorductSkusDTO,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductSkus(@PathVariable Long id){
        System.out.println(id);
        return productsSkusServices.deleteProductSkusById(id);
    }
}
