package com.spbstu.edu.advertisement.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {
    
    private final static String MESSAGE = "Category not found! ";
    
    public CategoryNotFoundException() {
        super(MESSAGE);
    }
    
    public CategoryNotFoundException(String message, Throwable cause) {
        super(MESSAGE + message, cause);
    }
    
    public CategoryNotFoundException(String message) {
        super(MESSAGE + message);
    }
    
    public CategoryNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
