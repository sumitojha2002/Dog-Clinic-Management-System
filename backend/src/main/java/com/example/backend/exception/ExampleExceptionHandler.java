package com.example.backend.exception;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExampleExceptionHandler {
    

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        ExampleException e = new ExampleException(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
    } 

    @ExceptionHandler(value={UserAlreadyExistException.class})
    public ResponseEntity<?> handleUserAlreadyExist(UserAlreadyExistException userAlreadyExist){
        ExampleException e = new ExampleException(userAlreadyExist.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(e,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        System.out.println("VALIDATION FAILED: " + e.getMessage());
            Map<String,Object> errors = new HashMap<>();
            e.getBindingResult().getFieldErrors().forEach(error->errors.put(error.getField(),error.getDefaultMessage()));

        Map<String,Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors",errors);
        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
    }
}
