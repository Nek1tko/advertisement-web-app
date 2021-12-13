package com.spbstu.edu.advertisement.dto;

import lombok.*;

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

    private String price;

    private Date creationDate;

    private UserDto saler;

    private MetroDto metro;

    private SubCategoryDto subCategory;

    private Boolean isActive;

    private Boolean complete;
}
