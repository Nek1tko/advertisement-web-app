package com.spbstu.edu.advertisement.dto;

import javassist.bytecode.ByteArray;
import lombok.*;

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
