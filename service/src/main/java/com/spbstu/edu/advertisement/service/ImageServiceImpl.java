package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.entity.Image;
import com.spbstu.edu.advertisement.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;


@Service
public class ImageServiceImpl implements ImageService {
    
    private final ImageRepository imageRepository;
    
    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }
    
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
