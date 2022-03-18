package com.spbstu.edu.advertisement.service.impl;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.dto.FavouriteDto;
import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.entity.User;
import com.spbstu.edu.advertisement.exception.CustomException;
import com.spbstu.edu.advertisement.exception.ExceptionId;
import com.spbstu.edu.advertisement.mapper.AdMapper;
import com.spbstu.edu.advertisement.repository.AdRepository;
import com.spbstu.edu.advertisement.repository.PageableAdRepository;
import com.spbstu.edu.advertisement.service.UserService;
import com.spbstu.edu.advertisement.vo.PageableContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AdServiceImplTest {
    
    @Mock
    private AdRepository adRepository;
    
    @Mock
    private PageableAdRepository pageableAdRepository;
    
    @Mock
    private AdMapper adMapper;
    
    @Mock
    private UserService userService;
    
    @InjectMocks
    private AdServiceImpl adService;
    
    @BeforeEach
    public void setUp() {
        User user = new User();
        long userId = 1L;
        user.setId(userId);
        Ad ad = createAd(2L);
        user.setFavouriteAds(Stream.of(ad).collect(Collectors.toList()));
        Mockito.lenient().when(userService.getUserEntity(user.getId())).thenReturn(user);
        
        AdDto adDto = AdDto.builder().id(2L).build();
        Mockito.lenient().when(adMapper.toAdDto(ad)).thenReturn(adDto);
        
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.lenient().when(authentication.getPrincipal()).thenReturn(user);
    }
    
    @Test
    void getAds() {
        PageableContext pageableContext = PageableContext.builder()
                .page(1)
                .build();
        List<Ad> adList = LongStream.rangeClosed(1L, 3L)
                .mapToObj(AdServiceImplTest::createAd)
                .collect(Collectors.toList());
        List<AdDto> adDtoList = LongStream.rangeClosed(1L, 3L)
                .mapToObj(id -> AdDto.builder().id(id).build())
                .collect(Collectors.toList());
        Mockito.when(pageableAdRepository.findAdsByFilters(pageableContext)).thenReturn(adList);
        for (int i = 0; i < 3; i++) {
            Mockito.when(adMapper.toAdDto(adList.get(i))).thenReturn(adDtoList.get(i));
        }
        
        List<AdDto> result = adService.getAds(pageableContext);
        assertEquals(3, result.size());
        
        for (int i = 0; i < 3; i++) {
            assertEquals(i + 1, result.get(i).getId());
        }
    }
    
    @Test
    void countAds() {
        PageableContext pageableContext = PageableContext.builder()
                .build();
        Mockito.when(pageableAdRepository.countAdsByFilter(pageableContext)).thenReturn(12L);
        assertEquals(12L, adService.countAds(pageableContext));
    }
    
    @Test
    void getAd() {
        Ad ad = createAd(2L);
        Mockito.when(adRepository.findById(2L)).thenReturn(Optional.of(ad));
        AdDto adDto = AdDto.builder().id(2L).build();
        Mockito.when(adMapper.toAdDto(ad)).thenReturn(adDto);
        AdDto result = adService.getAd(2L);
        assertEquals(2L, result.getId());
    }
    
    @Test
    void getAdThatNotExist() {
        try {
            adService.getAd(5L);
        } catch (CustomException customException) {
            assertEquals(ExceptionId.AD_NOT_NOT_FOUND, customException.getId());
        }
    }
    
    @Test
    void getAdCheckFavourite() {
        Ad favouriteAd = createAd(2L);
        Mockito.when(adRepository.findById(2L)).thenReturn(Optional.of(favouriteAd));
        AdDto favouriteAdDto = AdDto.builder().id(2L).build();
        Mockito.when(adMapper.toAdDto(favouriteAd)).thenReturn(favouriteAdDto);
        AdDto favouriteResult = adService.getAd(2L);
        
        assertEquals(2L, favouriteResult.getId());
        assertTrue(favouriteResult.getIsFavourite());
        
        Ad noFavouriteAd = createAd(1L);
        Mockito.when(adRepository.findById(1L)).thenReturn(Optional.of(noFavouriteAd));
        AdDto noFavouriteAdDto = AdDto.builder().id(1L).build();
        Mockito.when(adMapper.toAdDto(noFavouriteAd)).thenReturn(noFavouriteAdDto);
        AdDto noFavouriteResult = adService.getAd(1L);
        
        assertEquals(1L, noFavouriteResult.getId());
        assertFalse(noFavouriteResult.getIsFavourite());
    }
    
    @Test
    void addAd() {
        AdDto adDto = AdDto.builder().id(1L)
                .build();
        Mockito.when(adMapper.toAdDto(adRepository.save(adMapper.toAd(adDto)))).thenReturn(adDto);
        adDto = adService.addAd(adDto);
        assertNotNull(adDto.getCreationDate());
        assertTrue(adDto.getIsActive());
        assertFalse(adDto.getIsFavourite());
    }
    
    @Test
    void addAdCheckActiveStatus() {
        AdDto adDto1 = AdDto.builder().id(1L)
                .isActive(true)
                .build();
        Mockito.when(adMapper.toAdDto(adRepository.save(adMapper.toAd(adDto1)))).thenReturn(adDto1);
        AdDto result1 = adService.addAd(adDto1);
        assertTrue(result1.getIsActive());
        
        AdDto adDto2 = AdDto.builder().id(2L)
                .isActive(false)
                .build();
        Mockito.when(adMapper.toAdDto(adRepository.save(adMapper.toAd(adDto2)))).thenReturn(adDto2);
        AdDto result2 = adService.addAd(adDto2);
        assertFalse(result2.getIsActive());
        
        AdDto adDto3 = AdDto.builder().id(3L)
                .build();
        Mockito.when(adMapper.toAdDto(adRepository.save(adMapper.toAd(adDto3)))).thenReturn(adDto3);
        AdDto result3 = adService.addAd(adDto3);
        assertTrue(result3.getIsActive());
    }
    
    @Test
    void deleteAd() {
        assertDoesNotThrow(() -> adService.deleteAd(1L));
    }
    
    @Test
    void deleteAdThatNotExist() {
        Mockito.doThrow(new EmptyResultDataAccessException(0)).when(adRepository).deleteById(1L);
        try {
            adService.deleteAd(1L);
        } catch (CustomException customException) {
            assertEquals(ExceptionId.AD_NOT_NOT_FOUND, customException.getId());
        }
    }
    
    @Test
    void updateAd() {
        // already stored record
        Ad ad = createAd(2L);
        Mockito.when(adRepository.findById(2L)).thenReturn(Optional.of(ad));
        AdDto adDto = AdDto.builder().id(2L).name("name").build();
        Mockito.when(adMapper.toAdDto(ad)).thenReturn(adDto);
        
        // updated record
        AdDto adDtoUpdated = AdDto.builder().id(2L).isActive(false).build();
        
        // merge stored and updated records
        Ad adMerged = new Ad();
        adMerged.setId(2L);
        adMerged.setName("name");
        adMerged.setIsActive(false);
        Mockito.when(adMapper.updateWithNullAsNoChange(adDtoUpdated, adMapper.toAd(adDto)))
                .thenReturn(adMerged);
        AdDto adDtoMerged = AdDto.builder()
                .id(2L)
                .name("name")
                .isActive(false)
                .build();
        
        // other mocks
        Mockito.when(adRepository.save(adMerged)).thenReturn(adMerged);
        
        adDtoUpdated.setName("name");
        Mockito.when(adMapper.toAdDto(adMerged)).thenReturn(adDtoMerged);
        
        AdDto result = adService.updateAd(adDtoUpdated);
        assertFalse(result.getIsActive());
        assertEquals("name", result.getName());
        assertEquals(2L, result.getId());
        assertTrue(result.getIsFavourite());
    }
    
    @Test
    void getAdsByUserId() {
        List<Ad> adList = LongStream.rangeClosed(1L, 3L)
                .mapToObj(AdServiceImplTest::createAd)
                .collect(Collectors.toList());
        List<AdDto> adDtoList = LongStream.rangeClosed(1L, 3L)
                .mapToObj(id -> AdDto.builder().id(id).build())
                .collect(Collectors.toList());
        User user = userService.getUserEntity(1L);
        user.setUserAds(adList);
        for (int i = 0; i < 3; i++) {
            Mockito.when(adMapper.toAdDto(adList.get(i))).thenReturn(adDtoList.get(i));
        }
        
        List<AdDto> result = adService.getAds(1L);
        assertEquals(3, result.size());
        for (int i = 0; i < 3; i++) {
            assertEquals(i + 1, result.get(i).getId());
        }
        assertTrue(result.get(1).getIsFavourite());
    }
    
    @Test
    void getFavouriteAds() {
        List<AdDto> adDtoList = adService.getFavouriteAds(1L);
        
        assertEquals(1, adDtoList.size());
        assertTrue(adDtoList.get(0).getIsFavourite());
    }
    
    @Test
    void addToFavourites() {
        User user = userService.getUserEntity(1L);
        assertEquals(1, user.getFavouriteAds().size());
        
        Ad ad1 = createAd(1L);
        Mockito.when(adRepository.findById(1L)).thenReturn(Optional.of(ad1));
        
        adService.addToFavourites(FavouriteDto.builder().adId(1L).isFavourite(true).build());
        assertEquals(2, user.getFavouriteAds().size());
        assertEquals(1L, user.getFavouriteAds().get(1).getId());
        
        adService.addToFavourites(FavouriteDto.builder().adId(1L).isFavourite(true).build());
        assertEquals(2, user.getFavouriteAds().size());
        
        adService.addToFavourites(FavouriteDto.builder().adId(3L).isFavourite(false).build());
        assertEquals(2, user.getFavouriteAds().size());
        
        adService.addToFavourites(FavouriteDto.builder().adId(1L).isFavourite(false).build());
        assertEquals(1, user.getFavouriteAds().size());
        
        adService.addToFavourites(FavouriteDto.builder().adId(2L).isFavourite(true).build());
        assertEquals(1, user.getFavouriteAds().size());
        
        try {
            adService.addToFavourites(FavouriteDto.builder().adId(5L).isFavourite(true).build());
        } catch (CustomException customException) {
            assertEquals(ExceptionId.AD_NOT_NOT_FOUND, customException.getId());
        }
        
        assertEquals(1, user.getFavouriteAds().size());
    }
    
    
    private static Ad createAd(Long id) {
        Ad ad = new Ad();
        ad.setId(id);
        return ad;
    }
}
