package com.example.backend.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.ecommers.dto.CreateCategoryDTO;
import com.example.backend.services.ecommers.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminCategoriesController {
    private final CategoryService categoryService;
    
    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@Valid @ModelAttribute CreateCategoryDTO createCategoryDTO){
        return categoryService.createCategory(createCategoryDTO);
    }


}
