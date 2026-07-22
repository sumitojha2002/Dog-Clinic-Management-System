package com.example.backend.services.ecommers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.entity.ecommers.Category;
import com.example.backend.entity.ecommers.dto.CreateCategoryDTO;
import com.example.backend.helper.ProfileHelper;
import com.example.backend.repository.ecommers.CategoryRepository;
import com.example.backend.response.Response;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

    /*
        Things to achive from this service 
        
        -> GET /categories
        -> GET /categories/{id}
        -> POST /categories
        -> PUT /categories/{id}
        -> DELETE /categories/{id}
    */


@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
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


    public ResponseEntity<?> getAllCategories(){
        try{
            List<Category.displayAllCategory> listOfCat = categoryRepository.findAll()
                .stream()        
                .map(ProfileHelper::allCategoryMapper)
                .toList();
            if(listOfCat.isEmpty()){
                return Response.ResponseHandler("There are not categories yet.",HttpStatus.OK, listOfCat);
            }
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,listOfCat);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getCategoriesById(Long categoryId){
        try{
            Optional<Category> category = categoryRepository.findById(categoryId);
            
            if(!category.isPresent()){
                return Response.ResponseHandler("Category not found.", HttpStatus.NOT_FOUND);
            }
            
            Category.displayAllCategory categoryResult = ProfileHelper.allCategoryMapper(category.get());
            

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,categoryResult);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> updateCategory(Map<String,Object>update,Long id){
        try{
            Optional<Category> getCategory = categoryRepository.findById(id);

        if(!getCategory.isPresent()){
            return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
        Category category = getCategory.get();

        update.forEach((key,value)->{
            if(key.equals("name")) category.setName((String) value);
            if(key.equals("description"))category.setDescription((String) value);
        });

        Category.displayAllCategory rCategory = ProfileHelper.allCategoryMapper(category);

        categoryRepository.save(category);
        return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK, rCategory);
    }catch(Exception e){
        e.printStackTrace();
        return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @Transactional
    public ResponseEntity<?> deleteCategoryById(Long id){
        try{
            Optional<Category> category = categoryRepository.findById(id);

            if(!category.isPresent()){
                return Response.ResponseHandler("Could not find the category", HttpStatus.NOT_FOUND);
            }

            categoryRepository.deleteById(id);
            return Response.ResponseHandler("Category deleted successfully.", HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
