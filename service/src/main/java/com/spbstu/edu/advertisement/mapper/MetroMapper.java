package com.spbstu.edu.advertisement.mapper;

import com.spbstu.edu.advertisement.dto.MetroDto;
import com.spbstu.edu.advertisement.entity.Metro;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MetroMapper {
    Metro toMetro(MetroDto metroDto);
    
    MetroDto toMetroDto(Metro metro);
}
