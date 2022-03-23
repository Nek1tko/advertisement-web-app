package com.spbstu.edu.advertisement.controller;

import com.spbstu.edu.advertisement.dto.AdDto;
import com.spbstu.edu.advertisement.dto.MetroDto;
import com.spbstu.edu.advertisement.dto.SubCategoryDto;
import com.spbstu.edu.advertisement.dto.UserDto;
import com.spbstu.edu.advertisement.security.JwtTokenFilter;
import com.spbstu.edu.advertisement.service.MetroService;
import com.spbstu.edu.advertisement.service.SubCategoryService;
import com.spbstu.edu.advertisement.vo.PageableContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.ServletException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = "classpath:sql/init.sql")
public class AdControllerTest {
    @Autowired
    private AdController adController;

    @Autowired
    private MetroService metroService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private AuthController authController;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    private String phoneNumber = "+79602281337";
    private String password = "123";
    private UserDto createdUser;

    @BeforeEach
    public void init() throws IOException, ServletException {
        UserDto userDto = UserDto.builder()
                .phoneNumber(phoneNumber)
                .password(password)
                .build();

        createdUser = authController.signUp(userDto);
        ResponseEntity<Map<Object, Object>> response = authController.signIn(UserDto.builder()
                .phoneNumber(phoneNumber)
                .password(password)
                .build()
        );

        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + response.getBody().get("token"));

        jwtTokenFilter.doFilterInternal(httpServletRequest, new MockHttpServletResponse(), new MockFilterChain());
    }

    @Test
    @Transactional
    public void testCreateAndReceiveAd() {
        List<MetroDto> metroDtoList = metroService.getMetros();
        MetroDto metroDto = metroDtoList.stream().findFirst().orElse(null);
        SubCategoryDto subCategoryDto = subCategoryService.getSubCategory(1);

        AdDto adDto = AdDto.builder()
                .name("Квартира")
                .metro(metroDto)
                .subCategory(subCategoryDto)
                .price(10000000.00)
                .description("Просторная квартира в центре города")
                .isActive(true)
                .build();

        AdDto saveAdDto = adController.addAd(adDto);
        AdDto foundAdDto = adController.getAdById(saveAdDto.getId());

        assertEquals(saveAdDto.getId(), foundAdDto.getId());
        assertEquals(saveAdDto.getMetro().getId(), foundAdDto.getMetro().getId());
        assertEquals(saveAdDto.getMetro().getName(), foundAdDto.getMetro().getName());
        assertEquals(saveAdDto.getSubCategory().getId(), foundAdDto.getSubCategory().getId());
        assertEquals(saveAdDto.getSubCategory().getName(), foundAdDto.getSubCategory().getName());
        assertEquals(saveAdDto.getPrice(), foundAdDto.getPrice());
        assertEquals(saveAdDto.getDescription(), foundAdDto.getDescription());
        assertEquals(saveAdDto.getIsActive(), foundAdDto.getIsActive());
    }

    @Test
    @Transactional
    public void testReceiveAdsWithPaginationWithoutFilters() {
        PageableContext pageableContext = PageableContext
                .builder()
                .page(1)
                .build();

        List<AdDto> foundAdDtoList = adController.getAds(pageableContext);

        assertEquals(0, foundAdDtoList.size());

        List<MetroDto> metroDtoList = metroService.getMetros();
        MetroDto metroDto = metroDtoList.stream().findFirst().orElse(null);
        SubCategoryDto subCategoryDto = subCategoryService.getSubCategory(1);

        List<String> adNameList = Stream.of("Машина крутая", "Макароны гречневые", "Наушники беспроводные", "Мышка USB", "Телефон Xiaomi Redmi 10A", "Кружка для кофе", "Зарядка для айфона", "Ложка чайная", "Патч Stone Island", "Бабиджон обычный", "Парик блондинка").collect(Collectors.toList());
        Collections.shuffle(adNameList);
        List<Double> adPriceList = Stream.of(100.00, 300.00, 1000.00, 50000.00, 123.00, 1337.00, 99999.00, 88888.00, 45454.00, 45000.00, 80000.00).collect(Collectors.toList());
        Collections.shuffle(adPriceList);

        IntStream.range(0, adNameList.size()).boxed().map(index -> AdDto
                .builder()
                .name(adNameList.get(index))
                .price(adPriceList.get(index))
                .metro(metroDto)
                .subCategory(subCategoryDto)
                .build()
        ).forEach(adController::addAd);

        pageableContext.setPage(2);

        foundAdDtoList = adController.getAds(pageableContext);
        AdDto foundAdDto = foundAdDtoList.stream().findFirst().orElse(null);

        assertEquals(1, foundAdDtoList.size());
        assertNotNull(foundAdDto);
        assertTrue(adPriceList.stream().anyMatch( adPrice -> Objects.equals(adPrice, foundAdDto.getPrice())));
        assertTrue(adNameList.stream().anyMatch( adName -> Objects.equals(adName, foundAdDto.getName())));
    }


}
