package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.dto.FavouriteDto;
import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.vo.PageableContext;

import java.util.List;

public interface AdService {
    List<AdDto> getAds(PageableContext pageableContext);

    Long countAds(PageableContext pageableContext);
    
    AdDto getAd(long adId);
    
    AdDto addAd(AdDto adDto);
    
    void deleteAd(long adId);
    
    AdDto updateAd(AdDto adDto);
    
    List<AdDto> getAds(long userId);
    
    List<AdDto> getFavouriteAds(long userId);

    Ad getAdEntity(long adId);
    
    FavouriteDto addFavourite(FavouriteDto favourite);
    
    AdDto setFavourite(AdDto ad);
}
