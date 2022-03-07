package com.spbstu.edu.advertisement.service.impl;

import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.mapper.AdMapper;
import com.spbstu.edu.advertisement.repository.AdRepository;
import com.spbstu.edu.advertisement.repository.PageableAdRepository;
import com.spbstu.edu.advertisement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Ad ad = new Ad();
        ad.setId(12L);
        ad.setName("adName");
        Mockito.when(adRepository.findById(12L)).thenReturn(Optional.of(ad));
    }
    
    @Test
    void getAdEntity() {
        assertEquals(adService.getAdEntity(12L).getName(),"adName");
    }
}
