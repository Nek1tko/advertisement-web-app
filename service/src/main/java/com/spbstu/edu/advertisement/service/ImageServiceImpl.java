package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.entity.Image;
import com.spbstu.edu.advertisement.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    
    private final ImageRepository imageRepository;
    
    @Override
    public Image getImage(long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
    }
    
    @Override
    public void addImage(Image image) {
        imageRepository.save(image);
    }
    
    @Override
    public void deleteImage(long id) {
        try {
            imageRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new RuntimeException("There is no image with this ID");
        }
    }
}
