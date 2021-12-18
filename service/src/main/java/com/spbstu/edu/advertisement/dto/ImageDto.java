package com.spbstu.edu.advertisement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spbstu.edu.advertisement.entity.Ad;
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
public class ImageDto {
    private Long id;
    
    private String path;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Ad ad;
}
