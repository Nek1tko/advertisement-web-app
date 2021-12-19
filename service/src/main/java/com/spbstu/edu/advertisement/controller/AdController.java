package com.spbstu.edu.advertisement.controller;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.service.AdService;
import com.spbstu.edu.advertisement.vo.PageableContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ad")
@RequiredArgsConstructor
public class AdController {

    private final AdService adService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AdDto addAd(@RequestBody AdDto ad) {
        return adService.addAd(ad);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public AdDto updateAd(@RequestBody AdDto ad) {
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
        return adService.getAds(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/favourites/{userId}")
    public List<AdDto> getFavoriteAdsByUserId(@PathVariable Long userId) {
        return adService.getFavouriteAds(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<AdDto> getAds(@RequestBody PageableContext pageableContext) {
        return adService.getAds(pageableContext);
    }
}
