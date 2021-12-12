package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.dto.CategoryDto;
import com.spbstu.edu.advertisement.dto.SubCategoryDto;
import com.spbstu.edu.advertisement.entity.Category;
import com.spbstu.edu.advertisement.entity.SubCategory;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getCategories();
    
    List<SubCategoryDto> getSubCategories(long categoryId);
    
    CategoryDto getCategory(long categoryId);
}
