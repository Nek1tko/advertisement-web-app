package com.spbstu.edu.advertisement.service.impl;

import com.spbstu.edu.advertisement.dto.CategoryDto;
import com.spbstu.edu.advertisement.dto.SubCategoryDto;
import com.spbstu.edu.advertisement.entity.Category;
import com.spbstu.edu.advertisement.exception.CategoryNotFoundException;
import com.spbstu.edu.advertisement.mapper.CategoryMapper;
import com.spbstu.edu.advertisement.mapper.SubCategoryMapper;
import com.spbstu.edu.advertisement.repository.CategoryRepository;
import com.spbstu.edu.advertisement.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    private final CategoryMapper categoryMapper;
    
    private final SubCategoryMapper subCategoryMapper;
    
    @Override
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SubCategoryDto> getSubCategories(long categoryId) {
        return getCategoryEntity(categoryId).getSubCategories().stream()
                .map(subCategoryMapper::toSubCategoryDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public CategoryDto getCategory(long categoryId) {
        return categoryMapper.toCategoryDto(getCategoryEntity(categoryId));
    }
    
    private Category getCategoryEntity(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
    }
}
