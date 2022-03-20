package com.spbstu.edu.advertisement.service.impl;

import com.spbstu.edu.advertisement.dto.CategoryDto;
import com.spbstu.edu.advertisement.dto.SubCategoryDto;
import com.spbstu.edu.advertisement.entity.Category;
import com.spbstu.edu.advertisement.entity.SubCategory;
import com.spbstu.edu.advertisement.exception.CustomException;
import com.spbstu.edu.advertisement.exception.ExceptionId;
import com.spbstu.edu.advertisement.mapper.CategoryMapper;
import com.spbstu.edu.advertisement.mapper.SubCategoryMapper;
import com.spbstu.edu.advertisement.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private SubCategoryMapper subCategoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    private CategoryDto categoryDto;

    @BeforeEach
    private void setUp() {
        Long id = 1L;
        String name = "Недвижимость";

        category = new Category();
        category.setId(id);
        category.setName(name);

        categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
    }

    @Test
    public void testGetCategories() {
        when(categoryMapper.toCategoryDto(category)).thenReturn(categoryDto);
        when(categoryRepository.findAll()).thenReturn(Stream.of(category).collect(Collectors.toList()));

        List<CategoryDto> categories = categoryService.getCategories();

        CategoryDto foundCategoryDto = categories.stream().findFirst().orElse(null);

        assertEquals(1, categories.size());
        assertNotNull(foundCategoryDto);
        assertEquals(category.getId(), foundCategoryDto.getId());
        assertEquals(category.getName(), foundCategoryDto.getName());

        verify(categoryMapper).toCategoryDto(any());
        verify(categoryRepository).findAll();
        verifyNoMoreInteractions(categoryMapper, categoryRepository);
    }

    @Test
    public void testGetSubCategories() {
        Long subCategoryId = 123L;
        String categoryName = "Гараж";

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        subCategory.setName(categoryName);
        subCategory.setCategory(category);

        SubCategoryDto subCategoryDto = new SubCategoryDto();
        subCategoryDto.setId(subCategoryId);
        subCategoryDto.setName(categoryName);
        subCategoryDto.setCategory(categoryDto);

        category.setSubCategories(Stream.of(subCategory).collect(Collectors.toList()));

        when(subCategoryMapper.toSubCategoryDto(subCategory)).thenReturn(subCategoryDto);
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        List<SubCategoryDto> subCategories = categoryService.getSubCategories(category.getId());

        SubCategoryDto foundSubCategoryDto = subCategories.stream().findFirst().orElse(null);

        assertEquals(1, subCategories.size());
        assertNotNull(foundSubCategoryDto);
        assertEquals(subCategory.getId(), foundSubCategoryDto.getId());
        assertEquals(subCategory.getName(), foundSubCategoryDto.getName());
        assertEquals(categoryDto, foundSubCategoryDto.getCategory());

        verify(subCategoryMapper).toSubCategoryDto(any());
        verify(categoryRepository).findById(any());
        verifyNoMoreInteractions(subCategoryMapper, categoryRepository);
    }

    @Test
    public void testGetCategory() {
        when(categoryMapper.toCategoryDto(category)).thenReturn(categoryDto);
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        CategoryDto foundCategoryDto = categoryService.getCategory(category.getId());

        assertNotNull(foundCategoryDto);
        assertEquals(category.getId(), foundCategoryDto.getId());
        assertEquals(category.getName(), foundCategoryDto.getName());

        verify(categoryMapper).toCategoryDto(any());
        verify(categoryRepository).findById(any());
        verifyNoMoreInteractions(categoryMapper, categoryRepository);
    }

    @Test
    public void testSubCategoryNotFound() {
        long id = 910L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        CustomException thrownException = assertThrows(
                CustomException.class,
                () -> categoryService.getCategory(id)
        );

        assertEquals(ExceptionId.CATEGORY_NOT_FOUND, thrownException.getId());
        verify(categoryRepository).findById(any());
        verifyNoMoreInteractions(subCategoryMapper, categoryMapper, categoryRepository);
    }
}
