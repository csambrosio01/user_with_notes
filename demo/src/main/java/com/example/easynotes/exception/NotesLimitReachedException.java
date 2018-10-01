package com.example.easynotes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotesLimitReachedException extends RuntimeException {

    public NotesLimitReachedException(){
        super();
    }

    public NotesLimitReachedException(String message){
        super(message);
    }

    public NotesLimitReachedException(String message, Throwable cause){
        super(message, cause);
    }
}
