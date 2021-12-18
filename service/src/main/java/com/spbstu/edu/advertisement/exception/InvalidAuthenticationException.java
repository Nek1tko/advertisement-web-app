package com.spbstu.edu.advertisement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidAuthenticationException extends RuntimeException {
    private final static String MESSAGE = "Password or login is incorrect! ";
    
    public InvalidAuthenticationException() {
        super(MESSAGE);
    }
    
    public InvalidAuthenticationException(String message, Throwable cause) {
        super(MESSAGE + message, cause);
    }
    
    public InvalidAuthenticationException(String message) {
        super(MESSAGE + message);
    }
    
    public InvalidAuthenticationException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
