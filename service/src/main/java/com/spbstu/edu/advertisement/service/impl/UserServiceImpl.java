package com.spbstu.edu.advertisement.service.impl;

import com.spbstu.edu.advertisement.dto.UserDto;
import com.spbstu.edu.advertisement.entity.User;
import com.spbstu.edu.advertisement.exception.UserNotFoundException;
import com.spbstu.edu.advertisement.exception.UsernameReservedException;
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
        if (userRepository.findByPhoneNumber(userDto.getPhoneNumber()) != null) {
            throw new UsernameReservedException();
        }
        
        setEncodedPassword(userDto);
        User user = userRepository.save(userMapper.toUser(userDto));
        return userMapper.toUserDto(user);
    }
    
    @Override
    public UserDto updateUser(UserDto userDto) {
        setEncodedPassword(userDto);
        User user = userRepository.save(userMapper.toUser(userDto));
        return userMapper.toUserDto(user);
    }
    
    @Override
    public void deleteUser(long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException exception) {
            throw new UserNotFoundException();
        }
    }
    
    private User getUserEntity(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
    
    private void setEncodedPassword(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
    }
}
