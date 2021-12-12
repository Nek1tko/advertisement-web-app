package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.dto.ImageDto;

import java.util.List;

public interface AdService {
    List<AdDto> getAds();
    
    AdDto getAd(long adId);
    
    AdDto addAd(AdDto adDto);
    
    void deleteAd(long adId);
    
    AdDto updateAd(AdDto adDto);
    
    List<ImageDto> getImages(long adId);
}
