package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.dto.ImageDto;
import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.mapper.AdMapper;
import com.spbstu.edu.advertisement.mapper.ImageMapper;
import com.spbstu.edu.advertisement.repository.AdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    
    private final AdRepository adRepository;
    
    private final AdMapper adMapper;
    
    private final ImageMapper imageMapper;
    
    @Override
    public List<AdDto> getAds() {
        return adRepository.findAll().stream()
                .map(adMapper::toAdDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public AdDto getAd(long adId) {
        return adMapper.toAdDto(getAdEntity(adId));
    }
    
    @Override
    public AdDto addAd(AdDto adDto) {
        Ad ad = adRepository.save(adMapper.toAd(adDto));
        return adMapper.toAdDto(ad);
    }
    
    @Override
    public void deleteAd(long adId) {
        try {
            adRepository.deleteById(adId);
        } catch (EmptyResultDataAccessException exception) {
            throw new RuntimeException("There is no ad with this ID");
        }
    }
    
    @Override
    public AdDto updateAd(AdDto adDto) {
        Ad ad = adRepository.save(adMapper.toAd(adDto));
        return adMapper.toAdDto(ad);
    }
    
    @Override
    public List<ImageDto> getImages(long adId) {
        return getAdEntity(adId).getImages().stream()
                .map(imageMapper::toImageDto)
                .collect(Collectors.toList());
    }
    
    private Ad getAdEntity(long adId) {
        return adRepository.findById(adId)
                .orElseThrow(() -> new RuntimeException("Ad not found"));
    }
    
}
