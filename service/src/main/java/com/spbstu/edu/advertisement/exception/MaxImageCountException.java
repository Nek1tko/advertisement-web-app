package com.spbstu.edu.advertisement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MaxImageCountException extends RuntimeException {
    private final static String MESSAGE = "The ad already contains the maximum number of images! ";
    
    public MaxImageCountException() {
        super(MESSAGE);
    }
    
    public MaxImageCountException(String message, Throwable cause) {
        super(MESSAGE + message, cause);
    }
    
    public MaxImageCountException(String message) {
        super(MESSAGE + message);
    }
    
    public MaxImageCountException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
