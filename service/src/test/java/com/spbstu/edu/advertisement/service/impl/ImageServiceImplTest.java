package com.spbstu.edu.advertisement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.dto.ImageDto;
import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.entity.Image;
import com.spbstu.edu.advertisement.entity.User;
import com.spbstu.edu.advertisement.exception.CustomException;
import com.spbstu.edu.advertisement.exception.ExceptionId;
import com.spbstu.edu.advertisement.mapper.ImageMapper;
import com.spbstu.edu.advertisement.repository.ImageRepository;
import com.spbstu.edu.advertisement.service.AdService;
import com.spbstu.edu.advertisement.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {
    
    private ImageService imageService;
    
    @Mock
    private ImageRepository imageRepository;
    
    @Mock
    private ImageMapper imageMapper;
    
    @Mock
    private AdService adService;
    
    @BeforeEach
    void setUp() {
        imageService = new ImageServiceImpl(imageRepository, adService, imageMapper);
    }
    
    private ImageDto createImageDto(Long id, AdDto ad, String path) {
        return ImageDto.builder()
                .id(id)
                .ad(ad)
                .path(path)
                .build();
    }
    
    private Image createImage(Long id, Ad ad, String path) {
        Image image = new Image();
        image.setId(id);
        image.setAd(ad);
        image.setPath(path);
        return image;
    }
    
    @Test
    void getImageThatExists() {
        Image image = createImage(1L, null, "path");
        Mockito.when(imageRepository.findById(1L)).thenReturn(Optional.of(image));
        Mockito.when(imageMapper.toImageDto(image)).thenReturn(createImageDto(1L, null, "path"));
        
        ImageDto imageDto = imageService.getImage(1L);
        assertEquals(1L, imageDto.getId());
        assertEquals("path", imageDto.getPath());
    }
    
    @Test
    void getImageThatNotExists() {
        try {
            imageService.getImage(5L);
        } catch (CustomException customException) {
            assertEquals(ExceptionId.IMAGE_NOT_FOUND, customException.getId());
        }
    }
    
    @Test
    void getImages() {
        Ad ad = new Ad();
        ad.setId(1L);
        AdDto adDto = AdDto.builder().id(1L).build();
        
        List<Image> images = LongStream.rangeClosed(1L, 3L)
                .mapToObj(id -> createImage(id, ad, "path" + id))
                .collect(Collectors.toList());
        ad.setImages(images);
        
        List<ImageDto> imagesDto = LongStream.rangeClosed(1L, 3L)
                .mapToObj(id -> createImageDto(id, adDto, "path" + id))
                .collect(Collectors.toList());
        
        Mockito.when(adService.getAdEntity(1L)).thenReturn(ad);
        
        for (int i = 0; i < 3; i++) {
            Mockito.when(imageMapper.toImageDto(images.get(i))).thenReturn(imagesDto.get(i));
        }
        
        List<ImageDto> returnedImages = imageService.getImages(1L);
        assertEquals(3, returnedImages.size());
        for (int i = 0; i < 3; i++) {
            assertEquals(i + 1, returnedImages.get(i).getId());
            assertEquals("path" + (i + 1), returnedImages.get(i).getPath());
            assertEquals(1, returnedImages.get(i).getAd().getId());
        }
    }
    
    @Test
    void deleteImage() throws IOException {
        File file = new File(ImageServiceImpl.UPLOAD_PATH + "/path");
        File uploadDir = new File(ImageServiceImpl.UPLOAD_PATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        file.createNewFile();
        
        Image image = createImage(1L, null, "path");
        Mockito.when(imageRepository.findById(1L)).thenReturn(Optional.of(image));
        Mockito.when(imageMapper.toImageDto(image)).thenReturn(createImageDto(1L, null, "path"));
        
        assertDoesNotThrow(() -> imageService.deleteImage(1L));
    }
    
    @Test
    void deleteImageThatNotExist() {
        Mockito.doThrow(new EmptyResultDataAccessException(0)).when(imageRepository).deleteById(1L);
        Image image = createImage(1L, null, "path");
        Mockito.when(imageRepository.findById(1L)).thenReturn(Optional.of(image));
        Mockito.when(imageMapper.toImageDto(image)).thenReturn(createImageDto(1L, null, "path"));
        try {
            imageService.deleteImage(1L);
        } catch (CustomException customException) {
            assertEquals(ExceptionId.IMAGE_NOT_FOUND, customException.getId());
        }
    }
    
    @Test
    void uploadImage() {
        String uuidName = UUID.randomUUID() + ".test.txt";
        
        MockMultipartFile multipartFile = new MockMultipartFile(
                "test",
                uuidName,
                MediaType.TEXT_PLAIN_VALUE,
                "Test".getBytes()
        );
        
        assertDoesNotThrow(() -> setUpUploadImage(multipartFile, 1L, 1L, 1L, 1L));
    }
    
    @Test
    void uploadImageWithInvalidFile() throws IOException {
        ImageDto imageDto = createImageDto(1L, null, null);
        
        try {
            imageService.uploadImage(new ObjectMapper().writeValueAsString(imageDto), null);
        } catch (CustomException customException) {
            assertEquals(ExceptionId.INVALID_FILE, customException.getId());
        }
        try {
            MockMultipartFile multipartFile = new MockMultipartFile(
                    "test",
                    null,
                    MediaType.TEXT_PLAIN_VALUE,
                    "Test".getBytes()
            );
            imageService.uploadImage(new ObjectMapper().writeValueAsString(imageDto), multipartFile);
        } catch (CustomException customException) {
            assertEquals(ExceptionId.INVALID_FILE, customException.getId());
        }
        try {
            MockMultipartFile multipartFile = new MockMultipartFile(
                    "test",
                    "",
                    MediaType.TEXT_PLAIN_VALUE,
                    "Test".getBytes()
            );
            imageService.uploadImage(new ObjectMapper().writeValueAsString(imageDto), multipartFile);
        } catch (CustomException customException) {
            assertEquals(ExceptionId.INVALID_FILE, customException.getId());
        }
    }
    
    @Test
    void uploadImageWithMaxImageCount() throws IOException {
        try {
            
            Ad ad = new Ad();
            ad.setId(1L);
            
            List<Image> images = LongStream.rangeClosed(1L, 3L)
                    .mapToObj(id -> createImage(id, ad, "path" + id))
                    .collect(Collectors.toList());
            ad.setImages(images);
            Mockito.when(adService.getAdEntity(1L)).thenReturn(ad);
            
            MockMultipartFile multipartFile = new MockMultipartFile(
                    "test",
                    "test.txt",
                    MediaType.TEXT_PLAIN_VALUE,
                    "Test".getBytes()
            );
            ImageDto imageDto = createImageDto(4L, AdDto.builder().id(1L).build(), null);
            imageService.uploadImage(new ObjectMapper().writeValueAsString(imageDto), multipartFile);
        } catch (CustomException customException) {
            assertEquals(ExceptionId.MAX_IMAGE_COUNT, customException.getId());
        }
    }
    
    @Test
    void uploadImageWithNotEnoughRights() throws IOException {
        String uuidName = UUID.randomUUID() + ".test.txt";
        
        MockMultipartFile multipartFile = new MockMultipartFile(
                "test",
                uuidName,
                MediaType.TEXT_PLAIN_VALUE,
                "Test".getBytes()
        );
        
        try {
            setUpUploadImage(multipartFile, 1L, 1L, 2L, 1L);
        } catch (CustomException customException) {
            assertEquals(ExceptionId.NOT_ENOUGH_RIGHTS, customException.getId());
        }
    }
    
    private void setUpUploadImage(MockMultipartFile multipartFile, Long adId, Long imageId, Long salerId, Long userId) throws IOException {
        User user = new User();
        user.setId(userId);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        
        Ad ad = new Ad();
        ad.setId(adId);
        User saler = new User();
        saler.setId(salerId);
        ad.setSaler(saler);
        Image image = createImage(imageId, ad, ImageServiceImpl.UPLOAD_PATH + "/" + multipartFile.getOriginalFilename());
        ad.setImages(Collections.singletonList(image));
        
        AdDto adDto = new AdDto();
        adDto.setId(adId);
        ImageDto imageDto = createImageDto(imageId, adDto, ImageServiceImpl.UPLOAD_PATH + "/" + multipartFile.getOriginalFilename());
        
        Mockito.when(adService.getAdEntity(adId)).thenReturn(ad);
        
        imageService.uploadImage(new ObjectMapper().writeValueAsString(imageDto), multipartFile);
    }
}
