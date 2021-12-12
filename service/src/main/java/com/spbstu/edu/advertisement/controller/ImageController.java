package com.spbstu.edu.advertisement.controller;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.dto.ImageDto;
import com.spbstu.edu.advertisement.service.AdService;
import com.spbstu.edu.advertisement.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    
    private final ImageService imageService;
    
    private final AdService adService;
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ImageDto addImage(ImageDto image) {
        return imageService.addImage(image);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{adId}")
    public List<ImageDto> getImagesByAdId(@PathVariable Long adId) {
        return adService.getImages(adId);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{imageId}")
    public AdDto deleteImage(@PathVariable Long imageId) {
        return imageService.deleteImage(imageId);
    }
}
