package com.example.backend.services.ecommers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.entity.ecommers.ProductAttributes;
import com.example.backend.entity.ecommers.dto.CreateProductAttributeCategory;
import com.example.backend.helper.ProfileHelper;
import com.example.backend.repository.ecommers.ProductAttributesRepository;
import com.example.backend.response.Response;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductAttributeService {
    private final ProductAttributesRepository productAttributesRepo;

    public ResponseEntity<?> getProductAttributes(String type){
        try{
            List<ProductAttributes.productAttr> productAttributes = productAttributesRepo.getProductAttribute(type)
                .stream()
                .map(ProfileHelper::displayProductAttribute)
                .toList();

            if(productAttributes.isEmpty()){
                return Response.ResponseHandler("No product attributes found.", HttpStatus.NOT_FOUND, productAttributes);
            }    

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,productAttributes);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> createProductAttribute(CreateProductAttributeCategory createProductAttributeCategory){
        try{
            ProductAttributes productAttributes = new ProductAttributes();
            
            LocalDateTime now = LocalDateTime.now();
            
            productAttributes.setProductAttributesType(createProductAttributeCategory.getProductAttributesType());
            productAttributes.setValue(createProductAttributeCategory.getValue());
            productAttributes.setCreatedAt(now);
            
            productAttributesRepo.save(productAttributes);

            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK,productAttributes);
        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> deleteProductAttributeById(Long id){
        try{
            Optional<ProductAttributes> productAttributes = productAttributesRepo.findById(id);
            
            if(productAttributes.isPresent()){
                return Response.ResponseHandler(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }

            productAttributesRepo.deleteById(id);
            return Response.ResponseHandler(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);

        }catch(Exception e){
            e.printStackTrace();
            return Response.ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
