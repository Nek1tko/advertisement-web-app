package com.spbstu.edu.advertisement.controller;

import com.spbstu.edu.advertisement.dto.UserDto;
import com.spbstu.edu.advertisement.exception.CustomException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {
    
    @Autowired
    private UserController userController;
    
    @Autowired
    private AuthController authController;
    
    private static final String LOGIN = "12345";
    
    private static final String PASSWORD = "password";
    
    private static final String NAME = "name";
    
    private static final String SURNAME = "surname";
    
    private static final String POSTFIX_FOR_UPDATABLE_USER = "postfix";
    
    @Test
    @Transactional
    public void updateNameSurnameTest() {
        UserDto userDto1 = UserDto.builder()
                .phoneNumber(LOGIN)
                .password(PASSWORD)
                .name(NAME)
                .surname(SURNAME)
                .build();
        
        UserDto userDtoBeforeUpdate = authController.signUp(userDto1);
        
        assertEquals(LOGIN, userDtoBeforeUpdate.getPhoneNumber());
        assertEquals(NAME, userDtoBeforeUpdate.getName());
        assertEquals(SURNAME, userDtoBeforeUpdate.getSurname());
        
        UserDto userDto2 = UserDto.builder()
                .id(userDtoBeforeUpdate.getId())
                .name(NAME + POSTFIX_FOR_UPDATABLE_USER)
                .surname(SURNAME + POSTFIX_FOR_UPDATABLE_USER)
                .build();
        UserDto userDtoAfterUpdate = userController.updateUser(userDto2);
        assertEquals(NAME + POSTFIX_FOR_UPDATABLE_USER, userDtoAfterUpdate.getName());
        assertEquals(SURNAME + POSTFIX_FOR_UPDATABLE_USER, userDtoAfterUpdate.getSurname());
        assertEquals(userDtoAfterUpdate.getId(), userDtoBeforeUpdate.getId());
    }
    
    
    @Test
    @Transactional
    public void updatePasswordTest() {
        UserDto userDto1 = UserDto.builder()
                .phoneNumber(LOGIN)
                .password(PASSWORD)
                .build();
        
        UserDto userDtoBeforeUpdate = authController.signUp(userDto1);
        
        assertDoesNotThrow(() -> authController.signIn(UserDto.builder()
                .phoneNumber(LOGIN)
                .password(PASSWORD)
                .build()));
        assertEquals(LOGIN, userDtoBeforeUpdate.getPhoneNumber());
    
        UserDto userDto2 = UserDto.builder()
                .id(userDtoBeforeUpdate.getId())
                .password(PASSWORD)
                .newPassword(PASSWORD + POSTFIX_FOR_UPDATABLE_USER)
                .build();
        UserDto userDtoAfterUpdate = userController.updateUser(userDto2);
        assertNotEquals(userDtoAfterUpdate.getPassword(), userDtoBeforeUpdate.getPassword());
        assertEquals(userDtoAfterUpdate.getId(), userDtoBeforeUpdate.getId());
        
        
        assertDoesNotThrow(() -> authController.signIn(UserDto.builder()
                .phoneNumber(LOGIN)
                .password(PASSWORD + POSTFIX_FOR_UPDATABLE_USER)
                .build()));
        assertThrows(CustomException.class, () -> authController.signIn(UserDto.builder()
                .phoneNumber(LOGIN)
                .password(PASSWORD)
                .build()));
    }
    
    @Test
    @Transactional
    public void updateLoginTest() {
        UserDto userDtoBeforeUpdate = authController.signUp(UserDto.builder()
                .phoneNumber(LOGIN)
                .password(PASSWORD)
                .build());
        
        assertDoesNotThrow(() -> authController.signIn(UserDto.builder()
                .phoneNumber(LOGIN)
                .password(PASSWORD)
                .build()));
        assertEquals(LOGIN, userDtoBeforeUpdate.getPhoneNumber());
        
        
        UserDto userDtoAfterUpdate = userController.updateUser(UserDto.builder()
                .id(userDtoBeforeUpdate.getId())
                .phoneNumber(LOGIN + POSTFIX_FOR_UPDATABLE_USER)
                .build());
        
        assertEquals(userDtoAfterUpdate.getId(), userDtoBeforeUpdate.getId());
        assertEquals(LOGIN + POSTFIX_FOR_UPDATABLE_USER, userDtoAfterUpdate.getPhoneNumber());
        
        assertDoesNotThrow(() -> authController.signIn(UserDto.builder()
                .phoneNumber(LOGIN + POSTFIX_FOR_UPDATABLE_USER)
                .password(PASSWORD)
                .build()));
        assertThrows(CustomException.class, () -> authController.signIn(UserDto.builder()
                .phoneNumber(LOGIN)
                .password(PASSWORD)
                .build()));
    }
}
