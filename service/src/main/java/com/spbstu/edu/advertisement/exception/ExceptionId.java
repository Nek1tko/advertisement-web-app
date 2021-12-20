package com.spbstu.edu.advertisement.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionId {
    NOT_ENOUGH_RIGHTS("Not enough rights!", HttpStatus.UNAUTHORIZED),
    USERNAME_RESERVED("This phone number reserved by another user!", HttpStatus.UNAUTHORIZED),
    INVALID_AUTHENTICATION("Password or login is incorrect!", HttpStatus.BAD_REQUEST),
    INVALID_FILE("Invalid file!", HttpStatus.BAD_REQUEST),
    MAX_IMAGE_COUNT("The ad already contains the maximum number of images!", HttpStatus.BAD_REQUEST),
    SUB_CATEGORY_NOT_FOUND("SubCategory not found!", HttpStatus.NOT_FOUND),
    AD_NOT_NOT_FOUND("Ad not found!", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND("Category not found!", HttpStatus.NOT_FOUND),
    IMAGE_NOT_FOUND("Image not found!", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("User not found!", HttpStatus.NOT_FOUND);
    
    private final String message;
    
    private final HttpStatus status;
    
    ExceptionId(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public HttpStatus getStatus() {
        return status;
    }
}
