package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.dto.UserDto;
import com.spbstu.edu.advertisement.entity.User;
import com.spbstu.edu.advertisement.mapper.UserMapper;
import com.spbstu.edu.advertisement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    
    private final UserMapper userMapper;
    
    @Override
    public UserDto getUser(long userId) {
        return userMapper.toUserDto(getUserEntity(userId));
    }
    
    @Override
    public UserDto addUser(UserDto userDto) {
        User user = userRepository.save(userMapper.toUser(userDto));
        return userMapper.toUserDto(user);
    }
    
    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.save(userMapper.toUser(userDto));
        return userMapper.toUserDto(user);
    }
    
    @Override
    public void deleteUser(long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException exception) {
            throw new RuntimeException("There is no user with this ID");
        }
    }
    
    private User getUserEntity(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
