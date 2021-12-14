package com.spbstu.edu.advertisement.dto;

import javassist.bytecode.ByteArray;
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
    
    private ByteArray file;
    
    private String fileName;
    
    private Long adId;
}
