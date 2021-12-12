package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.dto.ImageDto;
import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.entity.Image;
import com.spbstu.edu.advertisement.mapper.ImageMapper;
import com.spbstu.edu.advertisement.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    
    private final ImageRepository imageRepository;
    
    private final ImageMapper imageMapper;
    
    private final AdService adService;
    
    @Override
    public ImageDto getImage(long imageId) {
        return imageMapper.toImageDto(getImageEntity(imageId));
    }
    
    @Override
    public ImageDto addImage(ImageDto imageDto) {
        Image image = imageRepository.save(imageMapper.toImage(imageDto));
        return imageMapper.toImageDto(image);
    }
    
    @Override
    public AdDto deleteImage(long imageId) {
        long adId = getImageEntity(imageId).getAd().getId();
        imageRepository.deleteById(imageId);
        return adService.getAd(adId);
    }
    
    private Image getImageEntity(long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));
    }
}
