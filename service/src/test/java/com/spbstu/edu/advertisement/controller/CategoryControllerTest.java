package com.spbstu.edu.advertisement.controller;


import com.spbstu.edu.advertisement.dto.CategoryDto;
import com.spbstu.edu.advertisement.dto.SubCategoryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = "classpath:sql/init.sql")
public class CategoryControllerTest {
    @Autowired
    private CategoryController categoryController;
    
    @Test
    @Transactional
    public void getCategoriesAndSubCategoriesFromDBTest() {
        List<CategoryDto> categoryDtoList = categoryController.getCategories();
        assertEquals(3, categoryDtoList.size());
        assertEquals("Недвижимость", categoryDtoList.get(0).getName());
        assertEquals("Транспорт", categoryDtoList.get(1).getName());
        assertEquals("Детские товары", categoryDtoList.get(2).getName());
        
        List<SubCategoryDto> subCategoryDtoList = categoryController.getSubCategoriesByCategoryId(categoryDtoList.get(0).getId());
        assertEquals(4, subCategoryDtoList.size());
        
        assertEquals("Квартира", subCategoryDtoList.get(0).getName());
        assertEquals("Гараж", subCategoryDtoList.get(1).getName());
        assertEquals("Дом", subCategoryDtoList.get(2).getName());
        assertEquals("Апартаменты", subCategoryDtoList.get(3).getName());
        
        subCategoryDtoList = categoryController.getSubCategoriesByCategoryId(categoryDtoList.get(1).getId());
        assertEquals(1, subCategoryDtoList.size());
        assertEquals("Машина", subCategoryDtoList.get(0).getName());
        
        subCategoryDtoList = categoryController.getSubCategoriesByCategoryId(categoryDtoList.get(2).getId());
        assertEquals(0, subCategoryDtoList.size());
    }
}
