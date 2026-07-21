package com.example.backend.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubCatById(@Valid @ModelAttribute CreateSubCategoryDTO createSubCategoryDTO,@PathVariable Long id){
        return subCategoryService.updateSubCategory(createSubCategoryDTO, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBySubCatId(@PathVariable Long id){
        return subCategoryService.deleteBySubCatId(id);
    }
}
