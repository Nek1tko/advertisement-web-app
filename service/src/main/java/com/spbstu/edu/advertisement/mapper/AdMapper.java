package com.spbstu.edu.advertisement.mapper;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.service.AdService;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static com.spbstu.edu.advertisement.service.impl.ImageServiceImpl.DEFAULT_IMAGE_PATH;
import static com.spbstu.edu.advertisement.service.impl.ImageServiceImpl.UPLOAD_PATH;

@Mapper(componentModel = "spring", uses = {MetroMapper.class, SubCategoryMapper.class, UserMapper.class, AdService.class})
public interface AdMapper {
    
    Ad toAd(AdDto adDto);
    
    @Mapping(expression = "java(\"" + UPLOAD_PATH + "/\" + (ad.getImages() == null || ad.getImages().isEmpty() ? \""
            + DEFAULT_IMAGE_PATH + "\" : ad.getImages().get(0).getPath()))", target = "previewImagePath")
    AdDto toAdDto(Ad ad);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Ad updateWithNullAsNoChange(AdDto adDto, @MappingTarget Ad ad);
}
