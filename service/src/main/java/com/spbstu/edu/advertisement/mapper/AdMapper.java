package com.spbstu.edu.advertisement.mapper;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.entity.Ad;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AdMapper {
    Ad toAd(AdDto adDto);
    
    AdDto toAdDto(Ad ad);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Ad updateWithNullAsNoChange(AdDto adDto, @MappingTarget Ad ad);
}
