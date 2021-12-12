package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.entity.Image;

import java.util.List;

public interface AdService {
    List<Ad> getAds();
    
    Ad getAd(long id);
    
    Ad addAd(Ad ad);
    
    void deleteAd(long id);
    
    Ad updateAd(Ad ad);
    
    List<Image> getImages(long adId);
}
