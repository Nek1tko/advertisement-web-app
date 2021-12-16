package com.spbstu.edu.advertisement.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    
    private final static String MESSAGE = "User not found! ";
    
    public UserNotFoundException() {
        super(MESSAGE);
    }
    
    public UserNotFoundException(String message, Throwable cause) {
        super(MESSAGE + message, cause);
    }
    
    public UserNotFoundException(String message) {
        super(MESSAGE + message);
    }
    
    public UserNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
