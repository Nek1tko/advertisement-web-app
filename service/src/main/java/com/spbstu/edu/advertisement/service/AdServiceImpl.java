package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.entity.User;
import com.spbstu.edu.advertisement.mapper.AdMapper;
import com.spbstu.edu.advertisement.repository.AdRepository;
import com.spbstu.edu.advertisement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    
    private final AdRepository adRepository;
    
    private final AdMapper adMapper;
    
    private final UserRepository userRepository;
    
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
    public List<AdDto> getAds(long userId) {
        return getUserEntity(userId).getUserAds().stream()
                .map(adMapper::toAdDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AdDto> getFavouriteAds(long userId) {
        return getUserEntity(userId).getFavouriteAds().stream()
                .map(adMapper::toAdDto)
                .collect(Collectors.toList());
    }
    
    private Ad getAdEntity(long adId) {
        return adRepository.findById(adId)
                .orElseThrow(() -> new RuntimeException("Ad not found"));
    }
    
    private User getUserEntity(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
