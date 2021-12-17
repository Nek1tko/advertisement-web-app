package com.spbstu.edu.advertisement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubCategoryDto {
    private Long id;
    
    private String name;
    
    private CategoryDto category;
}
