package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.entity.Image;

public interface ImageService {
    
    Image getImage(long id);
    
    void addImage(Image image);
    
    void deleteImage(long id);
}
