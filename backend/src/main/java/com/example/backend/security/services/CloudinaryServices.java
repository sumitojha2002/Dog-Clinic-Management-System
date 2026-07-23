package com.example.backend.security.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryServices {
    
    private final Cloudinary cloudinary;

    public String upload(MultipartFile file)throws Exception{
        try{
            Map<?,?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString(); 
        }catch(MaxUploadSizeExceededException e){
            throw e;
        }
    }

    public Map<?,?> delete(String publicId) throws IOException{
        try{    
            Map<?,?> options = ObjectUtils.asMap("invalidate",true);
            Map<?,?> response = cloudinary.uploader().destroy(publicId, options);
            return response;
        }catch(IOException e){
            throw e;
        }
    }
}
