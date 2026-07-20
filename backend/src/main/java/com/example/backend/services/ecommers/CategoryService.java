package com.example.backend.services.ecommers;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.entity.ecommers.Category;
import com.example.backend.entity.ecommers.dto.CreateCategoryDTO;
import com.example.backend.repository.ecommers.CategoryRepository;
import com.example.backend.response.Response;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ResponseEntity<?> createCategory(CreateCategoryDTO createCategoryDTO){
        try{

            LocalDateTime now = LocalDateTime.now();
            
            Category category = new Category();
            category.setName(createCategoryDTO.getName());
            category.setDescription(createCategoryDTO.getDescription());
            category.setCreatedAt(now);
            categoryRepository.save(category);

            return Response.ResponseHandler(HttpStatus.CREATED.getReasonPhrase(),HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<Category> getCategoryById(Long id){
        return categoryRepository.findById(id);
    }
}
