package com.spbstu.edu.advertisement.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Long id;

    private String name;
}
