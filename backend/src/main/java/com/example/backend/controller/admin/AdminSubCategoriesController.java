package com.example.backend.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.ecommers.dto.CreateSubCategoryDTO;
import com.example.backend.services.ecommers.SubCategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/subcategories")
public class AdminSubCategoriesController {
    private final SubCategoryService subCategoryService;

    @PostMapping
    public ResponseEntity<?> createSubCategory(@Valid @ModelAttribute CreateSubCategoryDTO createSubCategoryDTO){
        return subCategoryService.createSubCategoryService(createSubCategoryDTO);
    }
}
