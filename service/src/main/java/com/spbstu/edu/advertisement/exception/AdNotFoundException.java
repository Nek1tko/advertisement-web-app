package com.spbstu.edu.advertisement.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AdNotFoundException extends RuntimeException {
    
    private final static String MESSAGE = "Ad not found! ";
    
    public AdNotFoundException() {
        super(MESSAGE);
    }
    
    public AdNotFoundException(String message, Throwable cause) {
        super(MESSAGE + message, cause);
    }
    
    public AdNotFoundException(String message) {
        super(MESSAGE + message);
    }
    
    public AdNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
