package com.spbstu.edu.advertisement.service.impl;

import com.spbstu.edu.advertisement.dto.ImageDto;
import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.entity.Image;
import com.spbstu.edu.advertisement.mapper.ImageMapper;
import com.spbstu.edu.advertisement.repository.AdRepository;
import com.spbstu.edu.advertisement.repository.ImageRepository;
import com.spbstu.edu.advertisement.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    
    private final ImageRepository imageRepository;
    
    private final AdRepository adRepository;
    
    private final ImageMapper imageMapper;
    
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
    public void deleteImage(long imageId) {
        try {
            imageRepository.deleteById(imageId);
        } catch (EmptyResultDataAccessException exception) {
            throw new RuntimeException("There is no image with this ID");
        }
    }
    
    @Override
    public List<ImageDto> getImages(long adId) {
        return getAdEntity(adId).getImages().stream()
                .map(imageMapper::toImageDto)
                .collect(Collectors.toList());
    }
    
    private Image getImageEntity(long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));
    }
    
    private Ad getAdEntity(long adId) {
        return adRepository.findById(adId)
                .orElseThrow(() -> new RuntimeException("Ad not found"));
    }
}
