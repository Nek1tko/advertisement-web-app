package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.entity.Metro;
import com.spbstu.edu.advertisement.repository.MetroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetroServiceImpl implements MetroService {
    
    private final MetroRepository metroRepository;
    
    @Override
    public List<Metro> getMetros() {
        return metroRepository.findAll();
    }
}
