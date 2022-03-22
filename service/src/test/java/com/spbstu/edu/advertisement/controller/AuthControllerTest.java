package com.spbstu.edu.advertisement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spbstu.edu.advertisement.dto.UserDto;
import com.spbstu.edu.advertisement.exception.CustomException;
import com.spbstu.edu.advertisement.exception.ExceptionId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthControllerTest {
    
    @Autowired
    private AuthController authController;
    
    private static final String LOGIN = "12345";
    
    private static final String CORRECT_PASSWORD = "password";
    
    private static final String INCORRECT_PASSWORD = "psw";
    
    @Test
    @Transactional
    public void signUpAndLoginTest() throws JsonProcessingException {
        UserDto userDto = UserDto.builder()
                .phoneNumber(LOGIN)
                .password(CORRECT_PASSWORD)
                .build();
        
        authController.signUp(userDto);
        
        userDto = UserDto.builder()
                .phoneNumber(LOGIN)
                .password(CORRECT_PASSWORD)
                .build();
        ResponseEntity<Map<Object, Object>> response = authController.signIn(userDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(LOGIN, response.getBody().get("phoneNumber"));
        assertNotNull(response.getBody().get("token"));
        assertNotNull(response.getBody().get("id"));
        
        
        userDto = UserDto.builder()
                .phoneNumber(LOGIN)
                .password(INCORRECT_PASSWORD)
                .build();
        try {
            authController.signIn(userDto);
        } catch (Exception exception) {
            assertTrue(exception instanceof CustomException);
            assertEquals(ExceptionId.INVALID_AUTHENTICATION, ((CustomException) exception).getId());
        }
    }
}
