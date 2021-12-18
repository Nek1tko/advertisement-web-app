package com.spbstu.edu.advertisement.service.impl;

import com.spbstu.edu.advertisement.dto.ImageDto;
import com.spbstu.edu.advertisement.entity.Image;
import com.spbstu.edu.advertisement.exception.ImageNotFoundException;
import com.spbstu.edu.advertisement.exception.InvalidFileException;
import com.spbstu.edu.advertisement.exception.MaxImageCountException;
import com.spbstu.edu.advertisement.mapper.ImageMapper;
import com.spbstu.edu.advertisement.repository.ImageRepository;
import com.spbstu.edu.advertisement.service.AdService;
import com.spbstu.edu.advertisement.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    
    private final static int MAX_IMAGE_COUNT = 3;
    
    private final ImageRepository imageRepository;
    
    private final AdService adService;
    
    private final ImageMapper imageMapper;
    
    @Value("${upload.path}")
    private String uploadPath;
    
    @Override
    public ImageDto getImage(long imageId) {
        return imageMapper.toImageDto(getImageEntity(imageId));
    }
    
    @Override
    public ImageDto uploadImage(MultipartFile file) throws IOException {
        if (file != null && file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String resultFilename = UUID.randomUUID() + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            return ImageDto.builder()
                    .path(resultFilename)
                    .build();
        } else {
            throw new InvalidFileException();
        }
    }
    
    @Override
    public ImageDto addImage(ImageDto image) {
        File file = new File(uploadPath + "/" + image.getPath());
        if (getImages(image.getAd().getId()).size() == MAX_IMAGE_COUNT) {
            file.delete();
            throw new MaxImageCountException();
        }
        Image savedImage = imageRepository.save(imageMapper.toImage(image));
        return imageMapper.toImageDto(savedImage);
    }
    
    @Override
    public void deleteImage(long imageId) {
        try {
            ImageDto image = getImage(imageId);
            File file = new File(uploadPath + "/" + image.getPath());
            file.delete();
            imageRepository.deleteById(imageId);
        } catch (EmptyResultDataAccessException exception) {
            throw new ImageNotFoundException();
        }
    }
    
    @Override
    public List<ImageDto> getImages(long adId) {
        return adService.getAdEntity(adId).getImages().stream()
                .map(imageMapper::toImageDto)
                .collect(Collectors.toList());
    }
    
    private Image getImageEntity(long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(ImageNotFoundException::new);
    }
}
