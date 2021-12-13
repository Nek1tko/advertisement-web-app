package com.spbstu.edu.advertisement.service.impl;

import com.spbstu.edu.advertisement.dto.MetroDto;
import com.spbstu.edu.advertisement.mapper.MetroMapper;
import com.spbstu.edu.advertisement.repository.MetroRepository;
import com.spbstu.edu.advertisement.service.MetroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetroServiceImpl implements MetroService {
    
    private final MetroRepository metroRepository;
    
    private final MetroMapper metroMapper;
    
    @Override
    public List<MetroDto> getMetros() {
        return metroRepository.findAll().stream()
                .map(metroMapper::toMetroDto)
                .collect(Collectors.toList());
    }
}
