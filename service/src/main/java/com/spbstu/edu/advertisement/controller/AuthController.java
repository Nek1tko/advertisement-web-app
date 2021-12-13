package com.spbstu.edu.advertisement.controller;

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
    public ResponseEntity<UserDto> signIn(@RequestBody UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getPhoneNumber(),
                        userDto.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, tokenService.createToken(user.getPhoneNumber(), user.getPassword()))
                .body(userMapper.toUserDto(user));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signUp")
    public UserDto signUp(@RequestBody UserDto user) {
        return userService.addUser(user);
    }
}
