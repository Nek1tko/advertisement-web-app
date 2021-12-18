package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.dto.UserDto;
import com.spbstu.edu.advertisement.entity.User;


public interface UserService {
    UserDto getUser(long userId);
    
    UserDto addUser(UserDto userDto);
    
    UserDto updateUser(UserDto userDto);
    
    UserDto getUserByPhoneNumber(String phoneNumber);
    
    User getUserEntity(long userId);
    
    void deleteUser(long userId);
}