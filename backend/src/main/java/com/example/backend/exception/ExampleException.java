package com.example.backend.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExampleException {
    private String message;
    private String throwable;
    private HttpStatus status;

    public ExampleException(String message,HttpStatus status){
        this.message = message;
        this.status = status;
    }
}
