package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.entity.Category;
import com.spbstu.edu.advertisement.entity.SubCategory;
import com.spbstu.edu.advertisement.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
    
    @Override
    public List<SubCategory> getSubCategories(long id) {
        return getCategory(id).getSubCategories();
    }
    
    @Override
    public Category getCategory(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
