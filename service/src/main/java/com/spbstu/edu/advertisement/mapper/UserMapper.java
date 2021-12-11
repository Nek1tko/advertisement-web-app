package com.spbstu.edu.advertisement.mapper;

import com.spbstu.edu.advertisement.dto.UserDto;
import com.spbstu.edu.advertisement.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto userDto);
    
    UserDto toUserDto(User user);
}
