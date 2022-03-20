package com.spbstu.edu.advertisement;

import com.spbstu.edu.advertisement.exception.CustomException;
import com.spbstu.edu.advertisement.exception.ExceptionId;
import com.spbstu.edu.advertisement.vo.ExceptionData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RestResponseEntityExceptionHandlerTest {
    
    private final RestResponseEntityExceptionHandler handler = new RestResponseEntityExceptionHandler();
    
    @Mock
    private static HttpServletRequest request;
    
    private static final String URL = "test url";
    
    @Test
    void handleCustom() {
        Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer(URL));
        
        CustomException customException = new CustomException(ExceptionId.AD_NOT_NOT_FOUND, "message");
        ResponseEntity<ExceptionData> response = handler.handleCustom(request, customException);
        
        assertEquals(customException.getId().getStatus(), response.getStatusCode());
        assertEquals(URL, response.getBody().getPath());
        assertEquals(customException.getClass().getName(), response.getBody().getException());
        assertEquals(customException.toString(), response.getBody().getExceptionMessage());
    }
    
    @Test
    void handleError() {
        Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer(URL));
        
        Exception exception = new Exception("message");
        ResponseEntity<ExceptionData> response = handler.handleError(request, exception);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(URL, response.getBody().getPath());
        assertEquals(exception.getClass().getName(), response.getBody().getException());
        assertEquals(exception.getMessage(), response.getBody().getExceptionMessage());
    }
}
