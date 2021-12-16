package com.spbstu.edu.advertisement.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends RuntimeException {
    
    private final static String MESSAGE = "Image not found! ";
    
    public ImageNotFoundException() {
        super(MESSAGE);
    }
    
    public ImageNotFoundException(String message, Throwable cause) {
        super(MESSAGE + message, cause);
    }
    
    public ImageNotFoundException(String message) {
        super(MESSAGE + message);
    }
    
    public ImageNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
