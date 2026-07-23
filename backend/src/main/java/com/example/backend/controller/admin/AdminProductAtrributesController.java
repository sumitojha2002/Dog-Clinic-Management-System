package com.example.backend.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.backend.entity.ecommers.dto.CreateProductAttributeCategory;
import com.example.backend.services.ecommers.ProductAttributeService;

import jakarta.validation.Valid;

@RequestMapping("/admin/product-attribute")
public class AdminProductAtrributesController {
    public ProductAttributeService productAttrService;
    
    @PostMapping
    public ResponseEntity<?> createProductAttributes(@Valid @ModelAttribute CreateProductAttributeCategory createProductAttributeCategory){
        return productAttrService.createProductAttribute(createProductAttributeCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductAttributeById(Long id){
        return productAttrService.deleteProductAttributeById(id);
    }
}
