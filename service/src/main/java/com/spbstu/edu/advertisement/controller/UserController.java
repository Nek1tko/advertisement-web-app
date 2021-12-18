package com.spbstu.edu.advertisement.controller;


import com.spbstu.edu.advertisement.dto.UserDto;
import com.spbstu.edu.advertisement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    
    
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public UserDto updateAd(@RequestBody UserDto user) {
        return userService.updateUser(user);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUser(userId);
    }
    
}
