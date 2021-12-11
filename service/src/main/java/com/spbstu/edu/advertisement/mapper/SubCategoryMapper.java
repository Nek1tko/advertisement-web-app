package com.spbstu.edu.advertisement.mapper;

import com.spbstu.edu.advertisement.dto.SubCategoryDto;
import com.spbstu.edu.advertisement.entity.SubCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubCategoryMapper {
    SubCategory toSubCategory(SubCategoryDto subCategoryDto);
    
    SubCategoryDto toSubCategoryDto(SubCategory subCategory);
}
