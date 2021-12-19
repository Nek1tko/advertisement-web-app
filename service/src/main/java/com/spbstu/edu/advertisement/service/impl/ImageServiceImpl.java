package com.spbstu.edu.advertisement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spbstu.edu.advertisement.dto.ImageDto;
import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.entity.Image;
import com.spbstu.edu.advertisement.entity.User;
import com.spbstu.edu.advertisement.exception.ImageNotFoundException;
import com.spbstu.edu.advertisement.exception.InvalidFileException;
import com.spbstu.edu.advertisement.exception.MaxImageCountException;
import com.spbstu.edu.advertisement.exception.NotEnoughRightsException;
import com.spbstu.edu.advertisement.mapper.ImageMapper;
import com.spbstu.edu.advertisement.repository.ImageRepository;
import com.spbstu.edu.advertisement.service.AdService;
import com.spbstu.edu.advertisement.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
    public ImageDto uploadImage(String imageJson, MultipartFile file) throws IOException {
        ImageDto image = new ObjectMapper().readValue(imageJson, ImageDto.class);
        validateParams(image, file);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String resultFilename = UUID.randomUUID() + "." + file.getOriginalFilename();
        
        file.transferTo(new File(uploadPath + "/" + resultFilename));
        image.setPath(resultFilename);
        Image savedImage = imageRepository.save(imageMapper.toImage(image));
        return imageMapper.toImageDto(savedImage);
    }
    
    private void validateParams(ImageDto image, MultipartFile file) {
        if (file == null || file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
            throw new InvalidFileException();
        }
        Ad ad = adService.getAdEntity(image.getAd().getId());
        if (ad.getImages().size() == MAX_IMAGE_COUNT) {
            throw new MaxImageCountException();
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(ad.getSaler().getId(), user.getId())) {
            throw new NotEnoughRightsException("You cannot add an image to someone else's ad.");
        }
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
