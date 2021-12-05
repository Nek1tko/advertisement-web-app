package com.spbstu.edu.advertisement.controller;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.dto.ImageDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{adId}")
    public ImageDto addImage(@PathVariable Long adId, ImageDto image) {
        return image;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{adId}")
    public List<ImageDto> getImagesByAdId(@PathVariable Long adId) {
        return null;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{imageId}")
    public AdDto deleteImage(@PathVariable Long imageId) {
        return null;
    }
}
