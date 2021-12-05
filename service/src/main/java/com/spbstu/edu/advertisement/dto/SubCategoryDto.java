package com.spbstu.edu.advertisement.dto;

import lombok.*;

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
