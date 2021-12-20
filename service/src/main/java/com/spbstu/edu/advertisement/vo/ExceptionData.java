package com.spbstu.edu.advertisement.vo;

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
public class ExceptionData {
    private String path;
    
    private String exception;
    
    private String exceptionMessage;
}
