package com.example.backend.controller.admin;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.ecommers.dto.CreateProductDTO;
import com.example.backend.entity.ecommers.dto.CreateProductSkusDTO;
import com.example.backend.entity.ecommers.dto.UpdateProductDTO;
import com.example.backend.services.ecommers.ProductService;
import com.example.backend.services.ecommers.ProductsSkusServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductController {
    private final ProductService productService;
    private final ProductsSkusServices productsSkusServices;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postPorduct(@ModelAttribute CreateProductDTO createProductDTO){
            return productService.postProduct(createProductDTO);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProduct(@ModelAttribute UpdateProductDTO updateProductDTO,Long id){
        return productService.updateProduct(updateProductDTO,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        return productService.deleteProductById(id);
    }

    @PostMapping("/{productId}/skus")
    public ResponseEntity<?> createProductSkus(@Valid @ModelAttribute CreateProductSkusDTO createProductSkusDTO, @PathVariable Long productId){
        return productsSkusServices.createProductSkus(createProductSkusDTO,productId);
    }
}
