package com.spbstu.edu.advertisement.controller;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.service.AdService;
import com.spbstu.edu.advertisement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ad")
@RequiredArgsConstructor
public class AdController {
    
    private final AdService adService;
    
    private final UserService userService;
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AdDto addAd(AdDto ad) {
        return adService.addAd(ad);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public AdDto updateAd(AdDto ad) {
        return adService.updateAd(ad);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{adId}")
    public AdDto getAdById(@PathVariable Long adId) {
        return adService.getAd(adId);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/{userId}")
    public List<AdDto> getAdsByUserId(@PathVariable Long userId) {
        return userService.getAds(userId);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/favourites/{userId}")
    public List<AdDto> getFavoriteAdsByUserId(@PathVariable Long userId) {
        return userService.getFavouriteAds(userId);
    }
}
