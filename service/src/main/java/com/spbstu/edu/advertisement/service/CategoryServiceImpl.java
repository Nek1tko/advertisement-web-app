package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.entity.Category;
import com.spbstu.edu.advertisement.entity.SubCategory;
import com.spbstu.edu.advertisement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }
    
    @Override
    public List<SubCategory> listSubCategories(long id) {
        return getCategory(id).getSubCategories();
    }
    
    @Override
    public Category getCategory(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
