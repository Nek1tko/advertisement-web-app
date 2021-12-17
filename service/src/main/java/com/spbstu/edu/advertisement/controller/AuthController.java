package com.spbstu.edu.advertisement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spbstu.edu.advertisement.dto.UserDto;
import com.spbstu.edu.advertisement.entity.User;
import com.spbstu.edu.advertisement.mapper.UserMapper;
import com.spbstu.edu.advertisement.service.TokenService;
import com.spbstu.edu.advertisement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signIn")
    public ResponseEntity<Map<Object, Object>> signIn(@RequestBody UserDto userDto) throws JsonProcessingException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getPhoneNumber(),
                        userDto.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();
        Map<Object, Object> model = new HashMap<>();
        model.put("id", user.getId());
        model.put("phoneNumber", user.getPhoneNumber());
        model.put("token", tokenService.createToken(user.getPhoneNumber(), user.getPassword()));
        return ResponseEntity.ok()
                .body(model);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signUp")
    public UserDto signUp(@RequestBody UserDto user) {
        return userService.addUser(user);
    }
}
