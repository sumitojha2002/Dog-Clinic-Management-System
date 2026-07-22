package com.example.backend.controller.admin;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PatchMapping("/categories/{id}")
    public ResponseEntity<?> getAllCategories(@RequestBody Map<String,Object> update,@PathVariable Long id){
        return categoryService.updateCategory(update, id);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategories(@PathVariable Long id){
        return categoryService.deleteCategoryById(id);
    }
}
