package com.spbstu.edu.advertisement.vo;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageableContext {
    private Integer page;

    private Double minPrice;

    private Double maxPrice;

    private Long metroId;

    private Long categoryId;

    private Boolean isActive;
    
    private String title;
}
