package com.spbstu.edu.advertisement.service.impl;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.dto.FavouriteDto;
import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.entity.User;
import com.spbstu.edu.advertisement.exception.AdNotFoundException;
import com.spbstu.edu.advertisement.exception.NotEnoughRightsException;
import com.spbstu.edu.advertisement.mapper.AdMapper;
import com.spbstu.edu.advertisement.repository.AdRepository;
import com.spbstu.edu.advertisement.repository.PageableAdRepository;
import com.spbstu.edu.advertisement.service.AdService;
import com.spbstu.edu.advertisement.service.UserService;
import com.spbstu.edu.advertisement.vo.PageableContext;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    
    private final AdRepository adRepository;
    
    private final PageableAdRepository pageableAdRepository;
    
    private final AdMapper adMapper;
    
    private final UserService userService;
    
    @Override
    public List<AdDto> getAds(PageableContext pageableContext) {
        return pageableAdRepository
                .findAdsByFilters(pageableContext)
                .stream()
                .map(adMapper::toAdDto)
                .map(this::setFavourite)
                .collect(Collectors.toList());
    }
    
    @Override
    public Long countAds(PageableContext pageableContext) {
        return pageableAdRepository
                .countAdsByFilter(pageableContext);
    }
    
    @Override
    public AdDto getAd(long adId) {
        return setFavourite(adMapper.toAdDto(getAdEntity(adId)));
    }
    
    @Override
    public AdDto addAd(AdDto adDto) {
        adDto.setCreationDate(LocalDate.now(ZoneId.of("Europe/Moscow")));
        Ad ad = adRepository.save(adMapper.toAd(adDto));
        return adMapper.toAdDto(ad);
    }
    
    @Override
    public void deleteAd(long adId) {
        try {
            adRepository.deleteById(adId);
        } catch (EmptyResultDataAccessException exception) {
            throw new AdNotFoundException();
        }
    }
    
    @Override
    public AdDto updateAd(AdDto adDto) {
        AdDto storedAd = getAd(adDto.getId());
        Ad savedAd = adMapper.updateWithNullAsNoChange(adDto, adMapper.toAd(storedAd));
        savedAd = adRepository.save(savedAd);
        return setFavourite(adMapper.toAdDto(savedAd));
    }
    
    @Override
    public List<AdDto> getAds(long userId) {
        return userService.getUserEntity(userId).getUserAds().stream()
                .map(adMapper::toAdDto)
                .map(this::setFavourite)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AdDto> getFavouriteAds(long userId) {
        return userService.getUserEntity(userId).getFavouriteAds().stream()
                .map(adMapper::toAdDto)
                .peek(adDto -> adDto.setIsFavourite(true))
                .collect(Collectors.toList());
    }
    
    @Override
    public Ad getAdEntity(long adId) {
        return adRepository.findById(adId)
                .orElseThrow(AdNotFoundException::new);
    }
    
    @Override
    public FavouriteDto addFavourite(FavouriteDto favourite) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
        if (!Objects.equals(favourite.getUserId(), user.getId())) {
            throw new NotEnoughRightsException("You cannot add the ad to someone else's favourite list.");
        }
        
        user = userService.getUserEntity(favourite.getUserId());
        
        boolean isAlreadyFavourite = user.getFavouriteAds().stream()
                .anyMatch(ad -> ad.getId().equals(favourite.getAdId()));
        
        if (favourite.getIsFavourite() && !isAlreadyFavourite) {
            Ad ad = getAdEntity(favourite.getAdId());
            user.getFavouriteAds().add(ad);
        } else if (!favourite.getIsFavourite() && isAlreadyFavourite) {
            user.setFavouriteAds(
                    user.getFavouriteAds().stream()
                            .filter(ad -> !ad.getId().equals(favourite.getAdId()))
                            .collect(Collectors.toList()));
        }
        userService.updateUser(user);
        return favourite;
    }
    
    @Override
    public AdDto setFavourite(AdDto ad) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserEntity(currentUser.getId());
        ad.setIsFavourite(user.getFavouriteAds().stream()
                .anyMatch(favouriteAd -> favouriteAd.getId().equals(ad.getId())));
        return ad;
    }
}
