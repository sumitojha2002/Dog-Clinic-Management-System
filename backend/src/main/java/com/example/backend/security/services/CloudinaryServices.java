package com.example.backend.security.services;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryServices {
    
    private final Cloudinary cloudinary;

    public String upload(MultipartFile file)throws Exception{
    
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
         return uploadResult.get("secure_url").toString(); 
    }
}
