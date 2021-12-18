package com.spbstu.edu.advertisement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidFileException extends RuntimeException {
    private final static String MESSAGE = "Invalid file! ";
    
    public InvalidFileException() {
        super(MESSAGE);
    }
    
    public InvalidFileException(String message, Throwable cause) {
        super(MESSAGE + message, cause);
    }
    
    public InvalidFileException(String message) {
        super(MESSAGE + message);
    }
    
    public InvalidFileException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
