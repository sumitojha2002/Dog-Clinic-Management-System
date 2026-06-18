package com.example.backend.response;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
    
    public static ResponseEntity<Object> ResponseHandler(String message,HttpStatus status,Object data){
        Map <String,Object> map = new LinkedHashMap<>();
        map.put("message", message);
        map.put("status",status);
        map.put("data",data);

        return new ResponseEntity<>(map,status);
    }

    public static ResponseEntity<Object> ResponseHandler(String message,HttpStatus status){
        Map <String,Object> map = new LinkedHashMap<>();
        map.put("message", message);
        map.put("status",status);

        return new ResponseEntity<>(map,status);
    }
}
