package com.example.backend.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.backend.entity.ecommers.dto.UpdatePorductSkusDTO;
import com.example.backend.services.ecommers.ProductsSkusServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/admin/skus")
public class AdminProductSkusController {
    private ProductsSkusServices productsSkusServices;

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProductSkus(@ModelAttribute UpdatePorductSkusDTO updatePorductSkusDTO,@PathVariable Long id){
        return productsSkusServices.updateProductSkus(updatePorductSkusDTO,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductSkus(@PathVariabel Long id){
        return productsSkusServices.deleteProductSkusById(id);
    }
}
