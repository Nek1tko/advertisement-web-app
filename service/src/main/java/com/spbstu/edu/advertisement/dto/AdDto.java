package com.spbstu.edu.advertisement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdDto {
    private Long id;
    
    private String name;
    
    private String description;
    
    private Double price;
    
    private Date creationDate;
    
    private UserDto saler;
    
    private MetroDto metro;
    
    private SubCategoryDto subCategory;
    
    private Boolean isActive;
}
