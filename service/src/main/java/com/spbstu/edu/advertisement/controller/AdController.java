package com.spbstu.edu.advertisement.controller;

import com.spbstu.edu.advertisement.dto.AdDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ad")
public class AdController {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AdDto addAd(AdDto ad) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public AdDto updateAd(AdDto ad) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{adId}")
    public AdDto getAdById(@PathVariable Long adId) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/{userId}")
    public List<AdDto> getAdsByUserId(@PathVariable Long userId) {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/favourites/{userId}")
    public List<AdDto> getFavoriteAdsByUserId(@PathVariable Long userId) {
        return null;
    }
}
