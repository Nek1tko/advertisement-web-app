package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.entity.SubCategory;
import com.spbstu.edu.advertisement.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {
    
    private final SubCategoryRepository subCategoryRepository;
    
    @Autowired
    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }
    
    
    @Override
    public SubCategory getSubCategory(long id) {
        return subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));
    }
}
