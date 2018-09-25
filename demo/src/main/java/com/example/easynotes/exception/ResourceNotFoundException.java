package com.example.easynotes.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){
        super();
    }

    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}

/*
Notice the use of @ResponseStatus annotation in the above exception class. This will cause Spring boot
to respond with the specified HTTP status code whenever this exception is thrown from your controller.
 */