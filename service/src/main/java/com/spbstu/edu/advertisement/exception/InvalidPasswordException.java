package com.spbstu.edu.advertisement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidPasswordException extends RuntimeException {
    private final static String MESSAGE = "Password is incorrect! ";
    
    public InvalidPasswordException() {
        super(MESSAGE);
    }
    
    public InvalidPasswordException(String message, Throwable cause) {
        super(MESSAGE + message, cause);
    }
    
    public InvalidPasswordException(String message) {
        super(MESSAGE + message);
    }
    
    public InvalidPasswordException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
