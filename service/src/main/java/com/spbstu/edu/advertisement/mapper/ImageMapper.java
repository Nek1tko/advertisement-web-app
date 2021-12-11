package com.spbstu.edu.advertisement.mapper;

import com.spbstu.edu.advertisement.dto.ImageDto;
import com.spbstu.edu.advertisement.entity.Image;
import org.mapstruct.Mapper;

@Mapper
public interface ImageMapper {
    Image toImage(ImageDto imageDto);
    
    ImageDto toImageDto(Image image);
}
