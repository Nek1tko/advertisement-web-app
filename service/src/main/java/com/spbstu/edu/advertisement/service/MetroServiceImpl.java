package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.entity.Metro;
import com.spbstu.edu.advertisement.repository.MetroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetroServiceImpl implements MetroService {
    private final MetroRepository metroRepository;
    
    @Autowired
    public MetroServiceImpl(MetroRepository metroRepository) {
        this.metroRepository = metroRepository;
    }
    
    @Override
    public List<Metro> listMetros() {
        return metroRepository.findAll();
    }
}
