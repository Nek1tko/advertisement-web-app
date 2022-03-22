package com.spbstu.edu.advertisement.controller;

import com.spbstu.edu.advertisement.dto.AdDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdControllerTest {
    @Autowired
    private AdController adController;

    @Test
    @Transactional
    public void testAdController() {
        AdDto adDto = new AdDto();
        adDto.setName("Пиво");
        adDto.setDescription("Пивка для рывка!");
        AdDto savedAdDto = adController.addAd(adDto);
        AdDto foundAdDto = adController.getAdById(savedAdDto.getId());
        System.out.println(foundAdDto);
    }
}
