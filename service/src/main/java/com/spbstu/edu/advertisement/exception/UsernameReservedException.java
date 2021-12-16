package com.spbstu.edu.advertisement.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UsernameReservedException extends RuntimeException {
    
    private final static String MESSAGE = "This phone number reserved by another user! ";
    
    public UsernameReservedException() {
        super(MESSAGE);
    }
    
    public UsernameReservedException(String message, Throwable cause) {
        super(MESSAGE + message, cause);
    }
    
    public UsernameReservedException(String message) {
        super(MESSAGE + message);
    }
    
    public UsernameReservedException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
