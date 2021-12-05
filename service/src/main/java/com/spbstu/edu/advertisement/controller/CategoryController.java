package com.spbstu.edu.advertisement.controller;

import com.spbstu.edu.advertisement.dto.CategoryDto;
import com.spbstu.edu.advertisement.dto.SubCategoryDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CategoryDto> getCategories() {
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{categoryId}")
    public List<SubCategoryDto> getSubCategoriesByCategoryId(@PathVariable Long categoryId) {
        return null;
    }
}
