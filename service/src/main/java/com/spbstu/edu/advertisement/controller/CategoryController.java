package com.spbstu.edu.advertisement.controller;

import com.spbstu.edu.advertisement.dto.CategoryDto;
import com.spbstu.edu.advertisement.dto.SubCategoryDto;
import com.spbstu.edu.advertisement.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CategoryDto> getCategories() {
        return categoryService.getCategories();
    }
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{categoryId}")
    public List<SubCategoryDto> getSubCategoriesByCategoryId(@PathVariable Long categoryId) {
        return categoryService.getSubCategories(categoryId);
    }
}
