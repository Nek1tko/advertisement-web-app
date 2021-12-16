package com.spbstu.edu.advertisement.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SubCategoryNotFoundException extends RuntimeException {
    
    private final static String MESSAGE = "SubCategory not found! ";
    
    public SubCategoryNotFoundException() {
        super(MESSAGE);
    }
    
    public SubCategoryNotFoundException(String message, Throwable cause) {
        super(MESSAGE + message, cause);
    }
    
    public SubCategoryNotFoundException(String message) {
        super(MESSAGE + message);
    }
    
    public SubCategoryNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}

