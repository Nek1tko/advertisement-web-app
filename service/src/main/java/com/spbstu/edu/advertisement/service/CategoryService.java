package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.entity.Category;
import com.spbstu.edu.advertisement.entity.SubCategory;

import java.util.List;

public interface CategoryService {
    List<Category> listCategories();
    
    List<SubCategory> listSubCategories(long id);
    
    Category getCategory(long id);
}
