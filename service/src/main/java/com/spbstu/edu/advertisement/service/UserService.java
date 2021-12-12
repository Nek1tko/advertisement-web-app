package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.dto.UserDto;

import java.util.List;

public interface UserService {
    List<AdDto> getAds(long userId);
    
    List<AdDto> getFavouriteAds(long userId);
    
    UserDto getUser(long userId);
    
    UserDto addUser(UserDto userDto);
    
    UserDto updateUser(UserDto userDto);
    
    void deleteUser(long userId);
}
