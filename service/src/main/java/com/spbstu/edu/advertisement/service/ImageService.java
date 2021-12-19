package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    
    ImageDto getImage(long imageId);
    
    ImageDto uploadImage(String imageJson, MultipartFile file) throws IOException;
    
    void deleteImage(long imageId);
    
    List<ImageDto> getImages(long adId);
}
