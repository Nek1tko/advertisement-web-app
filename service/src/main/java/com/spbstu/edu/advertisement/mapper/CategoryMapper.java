package com.spbstu.edu.advertisement.mapper;

import com.spbstu.edu.advertisement.dto.CategoryDto;
import com.spbstu.edu.advertisement.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    Category toCategory(CategoryDto categoryDto);
    
    CategoryDto toCategoryDto(Category category);
}
