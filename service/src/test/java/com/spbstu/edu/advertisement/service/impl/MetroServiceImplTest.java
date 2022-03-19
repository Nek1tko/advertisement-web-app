package com.spbstu.edu.advertisement.service.impl;

import com.spbstu.edu.advertisement.dto.MetroDto;
import com.spbstu.edu.advertisement.entity.Metro;
import com.spbstu.edu.advertisement.mapper.MetroMapperImpl;
import com.spbstu.edu.advertisement.repository.MetroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MetroServiceImplTest {
    
    @Mock
    private MetroRepository metroRepository;
    
    private MetroServiceImpl metroService;
    
    @BeforeEach
    public void setUp() {
        List<Metro> metros = LongStream.rangeClosed(1L, 3L)
                .mapToObj(id -> createMetro(id, "name" + id))
                .collect(Collectors.toList());
        Mockito.when(metroRepository.findAll()).thenReturn(metros);
        
        metroService = new MetroServiceImpl(metroRepository, new MetroMapperImpl());
    }
    
    private Metro createMetro(Long id, String name) {
        Metro metro = new Metro();
        metro.setId(id);
        metro.setName(name);
        return metro;
    }
    
    @Test
    void getMetros() {
        List<MetroDto> metros = metroService.getMetros();
        assertEquals(3, metros.size());
        for (int i = 0; i < 3; i++) {
            MetroDto metro = metros.get(i);
            assertEquals(i + 1, metro.getId());
            assertEquals("name" + (i + 1), metro.getName());
        }
    }
}