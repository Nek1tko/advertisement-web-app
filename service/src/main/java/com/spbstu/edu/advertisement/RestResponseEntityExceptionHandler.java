package com.spbstu.edu.advertisement;

import com.spbstu.edu.advertisement.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<Map<Object, Object>> handleCustom(HttpServletRequest request, CustomException exception) {
        Map<Object, Object> model = new HashMap<>();
        model.put("url", request.getRequestURL());
        model.put("exception", exception.getClass().getName());
        model.put("exception_message", exception.toString());
        
        return ResponseEntity.status(exception.getId().getStatus()).body(model);
    }
    
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<Object, Object>> handleError(HttpServletRequest request, Exception exception) {
        Map<Object, Object> model = new HashMap<>();
        model.put("url", request.getRequestURL());
        model.put("exception", exception.getClass().getName());
        model.put("exception_message", exception.getMessage());
    
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
    }
    
}
