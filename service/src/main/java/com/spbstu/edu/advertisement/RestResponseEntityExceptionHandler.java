package com.spbstu.edu.advertisement;

import com.spbstu.edu.advertisement.exception.CustomException;
import com.spbstu.edu.advertisement.vo.ExceptionData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ExceptionData> handleCustom(HttpServletRequest request, CustomException exception) {
        ExceptionData exceptionData = ExceptionData.builder()
                .path(request.getRequestURL().toString())
                .exception(exception.getClass().getName())
                .exceptionMessage(exception.toString())
                .build();

        return ResponseEntity.status(exception.getId().getStatus()).body(exceptionData);
    }
    
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionData> handleError(HttpServletRequest request, Exception exception) {
        ExceptionData exceptionData = ExceptionData.builder()
                .path(request.getRequestURL().toString())
                .exception(exception.getClass().getName())
                .exceptionMessage(exception.getMessage())
                .build();
    
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionData);
    }
    
}
