package com.spbstu.edu.advertisement.service.impl;

import com.spbstu.edu.advertisement.dto.SubCategoryDto;
import com.spbstu.edu.advertisement.mapper.SubCategoryMapper;
import com.spbstu.edu.advertisement.repository.SubCategoryRepository;
import com.spbstu.edu.advertisement.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    
    private final SubCategoryRepository subCategoryRepository;
    
    private final SubCategoryMapper subCategoryMapper;
    
    @Override
    public SubCategoryDto getSubCategory(long subCategoryId) {
        return subCategoryMapper.toSubCategoryDto(
                subCategoryRepository.findById(subCategoryId)
                        .orElseThrow(() -> new RuntimeException("SubCategory not found")));
    }
}
