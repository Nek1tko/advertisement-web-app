package com.spbstu.edu.advertisement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate creationDate;
    
    private UserDto saler;
    
    private MetroDto metro;
    
    private SubCategoryDto subCategory;
    
    private Boolean isActive;
    
    private String previewImagePath;
}
