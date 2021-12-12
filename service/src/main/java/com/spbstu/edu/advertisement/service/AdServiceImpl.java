package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.entity.Image;
import com.spbstu.edu.advertisement.repository.AdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    
    private final AdRepository adRepository;
    
    @Override
    public List<Ad> getAds() {
        return adRepository.findAll();
    }
    
    @Override
    public Ad getAd(long id) {
        return adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ad not found"));
    }
    
    @Override
    public Ad addAd(Ad ad) {
        return adRepository.save(ad);
    }
    
    @Override
    public void deleteAd(long id) {
        try {
            adRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new RuntimeException("There is no ad with this ID");
        }
    }
    
    @Override
    public Ad updateAd(Ad ad) {
        return adRepository.save(ad);
    }
    
    @Override
    public List<Image> getImages(long adId) {
        return getAd(adId).getImages();
    }
}
