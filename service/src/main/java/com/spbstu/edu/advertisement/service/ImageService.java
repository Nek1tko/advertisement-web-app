package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    
    ImageDto getImage(long imageId);
    
    ImageDto uploadImage(MultipartFile file) throws IOException;
    
    ImageDto addImage(ImageDto image);
    
    void deleteImage(long imageId);
    
    List<ImageDto> getImages(long adId);
}
