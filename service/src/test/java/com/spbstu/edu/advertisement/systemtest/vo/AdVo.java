package com.spbstu.edu.advertisement.systemtest.vo;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdVo {
    private String name;

    private String description;

    private String price;

    private String metro;

    private String category;

    private String subCategory;

    private Boolean isActive;

    private String previewImagePath;

    private List<String> images = new ArrayList<>();
}
