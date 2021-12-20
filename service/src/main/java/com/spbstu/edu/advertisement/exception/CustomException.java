package com.spbstu.edu.advertisement.exception;

public class CustomException extends RuntimeException {
    
    private final ExceptionId id;
    
    public CustomException(ExceptionId id) {
        this.id = id;
    }
    
    public CustomException(ExceptionId id, String message) {
        super(message);
        this.id = id;
    }
    
    public CustomException(ExceptionId id, String message, Throwable cause) {
        super(message, cause);
        this.id = id;
    }
    
    public CustomException(ExceptionId id, Throwable cause) {
        super(cause);
        this.id = id;
    }
    
    public ExceptionId getId() {
        return id;
    }
    
    @Override
    public String toString() {
        String message = getMessage();
        return id.name() + ": " + id.getMessage() + (message == null ? "" : " " + message);
    }
    
    @Override
    public StackTraceElement[] getStackTrace() {
        return getCause() == null ? super.getStackTrace() : getCause().getStackTrace();
    }
}
