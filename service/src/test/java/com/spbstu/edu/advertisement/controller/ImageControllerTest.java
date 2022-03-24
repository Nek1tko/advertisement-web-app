package com.spbstu.edu.advertisement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.dto.ImageDto;
import com.spbstu.edu.advertisement.dto.UserDto;
import com.spbstu.edu.advertisement.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.spbstu.edu.advertisement.service.impl.ImageServiceImpl.UPLOAD_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ImageControllerTest {
    @Autowired
    private ImageController imageController;
    
    @Autowired
    private AdController adController;
    
    @Autowired
    private AuthController authController;
    
    @Autowired
    private EntityManager entityManager;
    
    private AdDto adDto = AdDto.builder().name("name").build();
    
    private UserDto userDto = UserDto.builder().phoneNumber(LOGIN).password(PASSWORD).build();
    
    private static final String LOGIN = "12345";
    
    private static final String PASSWORD = "password";
    
    @BeforeEach
    public void setUp() throws JsonProcessingException {
        userDto = authController.signUp(userDto);
        entityManager.flush();
        entityManager.clear();
        authController.signIn(UserDto.builder()
                .phoneNumber(LOGIN)
                .password(PASSWORD)
                .build());
        
        User user = new User();
        user.setId(userDto.getId());
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        adDto.setSaler(UserDto.builder().id(userDto.getId()).build());
        adDto = adController.addAd(adDto);
        entityManager.flush();
        entityManager.clear();
    }
    
    @Test
    @Transactional
    public void uploadImageToAdAndGetTest() throws IOException {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "test",
                UUID.randomUUID() + ".test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Test".getBytes()
        );
        ImageDto imageDto = ImageDto.builder()
                .ad(AdDto.builder().id(adDto.getId()).build())
                .build();
        
        imageController.uploadImage(new ObjectMapper().writeValueAsString(imageDto), multipartFile);
        entityManager.flush();
        entityManager.clear();
        List<ImageDto> images = imageController.getImagesByAdId(adDto.getId());
        assertEquals(1, images.size());
        
        File filePath = new File(UPLOAD_PATH + "/" + images.get(0).getPath());
        assertTrue(filePath.exists() && !filePath.isDirectory());
    }
    
    @Test
    @Transactional
    public void uploadImageToAdDeleteAndCheckTest() throws IOException {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "test",
                UUID.randomUUID() + ".test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Test".getBytes()
        );
        ImageDto imageDto = ImageDto.builder()
                .ad(AdDto.builder().id(adDto.getId()).build())
                .build();
        
        ImageDto savedImage = imageController.uploadImage(new ObjectMapper().writeValueAsString(imageDto), multipartFile);
        entityManager.flush();
        entityManager.clear();
        List<ImageDto> images = imageController.getImagesByAdId(adDto.getId());
        assertEquals(1, images.size());
        
        File filePath = new File(UPLOAD_PATH + "/" + images.get(0).getPath());
        assertTrue(filePath.exists() && !filePath.isDirectory());
        
        
        imageController.deleteImage(savedImage.getId());
        entityManager.flush();
        entityManager.clear();
        images = imageController.getImagesByAdId(adDto.getId());
        assertEquals(0, images.size());
        assertFalse(filePath.exists());
    }
}
