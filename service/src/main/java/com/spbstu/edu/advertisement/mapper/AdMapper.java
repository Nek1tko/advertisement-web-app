package com.spbstu.edu.advertisement.mapper;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.entity.Ad;
import org.mapstruct.Mapper;

@Mapper
public interface AdMapper {
    Ad toAd(AdDto adDto);
    
    AdDto toAdDto(Ad ad);
}
