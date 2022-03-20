package com.spbstu.edu.advertisement.service.impl;

import com.spbstu.edu.advertisement.dto.SubCategoryDto;
import com.spbstu.edu.advertisement.entity.SubCategory;
import com.spbstu.edu.advertisement.exception.CustomException;
import com.spbstu.edu.advertisement.exception.ExceptionId;
import com.spbstu.edu.advertisement.mapper.SubCategoryMapper;
import com.spbstu.edu.advertisement.repository.SubCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubCategoryServiceTest {
    @Mock
    private SubCategoryRepository subCategoryRepository;

    @Mock
    private SubCategoryMapper subCategoryMapper;

    @InjectMocks
    private SubCategoryServiceImpl subCategoryService;

    @Test
    public void testGetSubCategory() {
        Long id = 3123L;
        String name = "Игрушки";

        SubCategory subCategory = new SubCategory();
        subCategory.setId(id);
        subCategory.setName(name);

        SubCategoryDto subCategoryDto = new SubCategoryDto();
        subCategoryDto.setId(id);
        subCategoryDto.setName(name);

        when(subCategoryRepository.findById(id)).thenReturn(Optional.of(subCategory));
        when(subCategoryMapper.toSubCategoryDto(subCategory)).thenReturn(subCategoryDto);

        SubCategoryDto foundSubCategoryDto = subCategoryService.getSubCategory(id);

        assertNotNull(foundSubCategoryDto);
        assertEquals(id, foundSubCategoryDto.getId());
        assertEquals(name, foundSubCategoryDto.getName());

        verify(subCategoryMapper).toSubCategoryDto(any());
        verify(subCategoryRepository).findById(any());
        verifyNoMoreInteractions(subCategoryMapper, subCategoryRepository);
    }

    @Test
    public void testSubCategoryNotFound() {
        long id = 100L;
        when(subCategoryRepository.findById(id)).thenReturn(Optional.empty());

        CustomException thrownException = assertThrows(
                CustomException.class,
                () -> subCategoryService.getSubCategory(id)
        );

        assertEquals(ExceptionId.SUB_CATEGORY_NOT_FOUND, thrownException.getId());
        verify(subCategoryRepository).findById(any());
        verifyNoMoreInteractions(subCategoryMapper, subCategoryRepository);
    }
}
