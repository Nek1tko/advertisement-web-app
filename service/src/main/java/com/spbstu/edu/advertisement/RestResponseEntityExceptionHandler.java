package com.spbstu.edu.advertisement;

import com.spbstu.edu.advertisement.exception.AdNotFoundException;
import com.spbstu.edu.advertisement.exception.CategoryNotFoundException;
import com.spbstu.edu.advertisement.exception.ImageNotFoundException;
import com.spbstu.edu.advertisement.exception.SubCategoryNotFoundException;
import com.spbstu.edu.advertisement.exception.UserNotFoundException;
import com.spbstu.edu.advertisement.exception.UsernameReservedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {UserNotFoundException.class, AdNotFoundException.class,
            SubCategoryNotFoundException.class, CategoryNotFoundException.class,
            ImageNotFoundException.class, UsernameReservedException.class})
    protected ResponseEntity<Object> handle(RuntimeException ex, HttpStatus status) {
        return ResponseEntity.status(status).body(ex.getMessage());
    }
}
