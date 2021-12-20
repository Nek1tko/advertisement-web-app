package com.spbstu.edu.advertisement.service.impl;

import com.spbstu.edu.advertisement.dto.UserDto;
import com.spbstu.edu.advertisement.entity.User;
import com.spbstu.edu.advertisement.exception.CustomException;
import com.spbstu.edu.advertisement.exception.ExceptionId;
import com.spbstu.edu.advertisement.mapper.UserMapper;
import com.spbstu.edu.advertisement.repository.UserRepository;
import com.spbstu.edu.advertisement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    
    private final UserMapper userMapper;
    
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserDto getUser(long userId) {
        return userMapper.toUserDto(getUserEntity(userId));
    }
    
    @Override
    public UserDto addUser(UserDto userDto) {
        if (getUserByPhoneNumber(userDto.getPhoneNumber()) != null) {
            throw new CustomException(ExceptionId.USERNAME_RESERVED);
        }
        
        setEncodedPassword(userDto);
        User user = userRepository.save(userMapper.toUser(userDto));
        return userMapper.toUserDto(user);
    }
    
    @Override
    public UserDto updateUser(UserDto userDto) {
        UserDto storedUser = getUser(userDto.getId());
        if (userDto.getNewPassword() != null) {
            if (passwordEncoder.matches(userDto.getPassword(), storedUser.getPassword())) {
                userDto.setPassword(userDto.getNewPassword());
                setEncodedPassword(userDto);
            } else {
                throw new CustomException(ExceptionId.INVALID_AUTHENTICATION);
            }
        } else if (userDto.getPassword() != null) {
            userDto.setPassword(null);
        }
        if (userDto.getPhoneNumber() != null) {
            UserDto userByPhoneNumber = getUserByPhoneNumber(userDto.getPhoneNumber());
            if (userByPhoneNumber != null && !userByPhoneNumber.getId().equals(userDto.getId())) {
                throw new CustomException(ExceptionId.USERNAME_RESERVED);
            }
        }
        User savedUser = userMapper.updateWithNullAsNoChange(userDto, userMapper.toUser(storedUser));
        savedUser = userRepository.save(savedUser);
        return userMapper.toUserDto(savedUser);
    }
    
    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    @Override
    public UserDto getUserByPhoneNumber(String phoneNumber) {
        return userMapper.toUserDto(userRepository.findByPhoneNumber(phoneNumber));
    }
    
    @Override
    public void deleteUser(long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException exception) {
            throw new CustomException(ExceptionId.USER_NOT_FOUND);
        }
    }
    
    @Override
    public User getUserEntity(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionId.USER_NOT_FOUND));
    }
    
    private void setEncodedPassword(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
    }
}
