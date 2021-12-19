package com.spbstu.edu.advertisement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class NotEnoughRightsException extends RuntimeException {
    private final static String MESSAGE = "Not enough rights! ";
    
    public NotEnoughRightsException() {
        super(MESSAGE);
    }
    
    public NotEnoughRightsException(String message, Throwable cause) {
        super(MESSAGE + message, cause);
    }
    
    public NotEnoughRightsException(String message) {
        super(MESSAGE + message);
    }
    
    public NotEnoughRightsException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
