package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.dto.ImageDto;

import java.util.List;

public interface ImageService {
    
    ImageDto getImage(long imageId);
    
    ImageDto addImage(ImageDto imageDto);
    
    void deleteImage(long imageId);
    
    List<ImageDto> getImages(long adId);
}
