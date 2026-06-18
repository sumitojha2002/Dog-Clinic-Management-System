package com.example.backend.exception;

public class UserAlreadyExistException  extends RuntimeException{
    public UserAlreadyExistException(String message){
        super(message);
    }
    public UserAlreadyExistException(String message,Throwable throwable){
        super(message,throwable);
    }
}
