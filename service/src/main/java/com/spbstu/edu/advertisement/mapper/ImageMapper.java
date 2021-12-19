package com.spbstu.edu.advertisement.mapper;

import com.spbstu.edu.advertisement.dto.ImageDto;
import com.spbstu.edu.advertisement.dto.UserDto;
import com.spbstu.edu.advertisement.entity.Image;
import com.spbstu.edu.advertisement.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image toImage(ImageDto imageDto);
    
    ImageDto toImageDto(Image image);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Image updateWithNullAsNoChange(ImageDto imageDto, @MappingTarget Image image);
}
