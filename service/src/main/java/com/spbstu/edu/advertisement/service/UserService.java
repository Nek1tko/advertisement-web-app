package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.dto.UserDto;


public interface UserService {
    UserDto getUser(long userId);
    
    UserDto addUser(UserDto userDto);
    
    UserDto updateUser(UserDto userDto);
    
    void deleteUser(long userId);
}