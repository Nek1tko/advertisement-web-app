package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.dto.ImageDto;

public interface ImageService {
    
    ImageDto getImage(long imageId);
    
    ImageDto addImage(ImageDto imageDto);
    
    AdDto deleteImage(long imageId);
}
