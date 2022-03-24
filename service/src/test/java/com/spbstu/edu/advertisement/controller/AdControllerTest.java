package com.spbstu.edu.advertisement.controller;

import com.spbstu.edu.advertisement.dto.*;
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

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
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

    @Autowired
    private EntityManager entityManager;

    private UserDto createdUser;

    @BeforeEach
    public void init() throws IOException, ServletException {
        String phoneNumber = "+79602281337";
        String password = "123";

        UserDto userDto = UserDto.builder()
                .phoneNumber(phoneNumber)
                .password(password)
                .build();

        createdUser = authController.signUp(userDto);
        entityManager.flush();
        entityManager.clear();
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
    public void testCreateAndReceive() {
        List<MetroDto> metroDtoList = metroService.getMetros();
        MetroDto metroDto = metroDtoList.stream().findFirst().orElse(null);
        SubCategoryDto subCategoryDto = subCategoryService.getSubCategory(1);

        AdDto adDto = AdDto.builder()
                .name("Квартира")
                .saler(createdUser)
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
    public void testReceiveWithPaginationWithoutFilters() {
        PageableContext pageableContext = PageableContext
                .builder()
                .page(1)
                .build();

        List<AdDto> foundAdDtoList = adController.getAds(pageableContext);

        assertEquals(0, foundAdDtoList.size());

        List<AdDto> adDtoList = createTestAds();

        pageableContext.setPage(2);

        foundAdDtoList = adController.getAds(pageableContext);
        AdDto foundAdDto = foundAdDtoList.stream().findFirst().orElse(null);

        assertEquals(1, foundAdDtoList.size());
        assertNotNull(foundAdDto);
        assertTrue(adDtoList.stream().anyMatch(adDto -> Objects.equals(adDto.getPrice(), foundAdDto.getPrice())));
        assertTrue(adDtoList.stream().anyMatch(adDto -> Objects.equals(adDto.getName(), foundAdDto.getName())));
    }

    @Test
    @Transactional
    public void testReceiveWithPaginationWithFilters() {
        PageableContext pageableContext = PageableContext
                .builder()
                .page(1)
                .build();

        List<AdDto> foundAdDtoList = adController.getAds(pageableContext);

        assertEquals(0, foundAdDtoList.size());

        createTestAds();

        Double maxPrice = 1000.00;
        Double minPrice = 123.00;
        Long categoryId = 1L;
        Long metroId = 1L;

        pageableContext.setPage(1);
        pageableContext.setMaxPrice(maxPrice);
        pageableContext.setMinPrice(minPrice);
        pageableContext.setIsActive(true);
        pageableContext.setCategoryId(categoryId);
        pageableContext.setMetroId(metroId);

        foundAdDtoList = adController.getAds(pageableContext);

        assertEquals(3, foundAdDtoList.size());
        assertFalse(foundAdDtoList.stream().anyMatch(adDto -> adDto.getPrice() > maxPrice));
        assertFalse(foundAdDtoList.stream().anyMatch(adDto -> adDto.getPrice() < minPrice));
        assertFalse(foundAdDtoList.stream().anyMatch(adDto -> !Objects.equals(adDto.getSubCategory().getCategory().getId(), categoryId)));
        assertFalse(foundAdDtoList.stream().anyMatch(adDto -> !Objects.equals(adDto.getMetro().getId(), metroId)));
    }

    @Test
    @Transactional
    public void testCreateAndUpdate() {
        List<MetroDto> metroDtoList = metroService.getMetros();
        MetroDto metroDto = metroDtoList.get(1);
        SubCategoryDto subCategoryDto = subCategoryService.getSubCategory(1);
        String adName = "Мышкааааа беспроводная";
        Double adPrice = 1200.00;

        AdDto adDto = AdDto
                .builder()
                .name(adName)
                .price(adPrice)
                .metro(metroDto)
                .subCategory(subCategoryDto)
                .isActive(true)
                .build();

        AdDto savedAdDto = adController.addAd(adDto);
        AdDto foundNewAdDto = adController.getAdById(savedAdDto.getId());

        MetroDto newMetroDto = metroDtoList.get(2);
        SubCategoryDto newSubCategoryDto = subCategoryService.getSubCategory(2);
        String newAdName = "Мышка беспроводная!";
        Double newAdPrice = 1400.00;

        AdDto updatedAdDto = AdDto
                .builder()
                .id(savedAdDto.getId())
                .name(newAdName)
                .price(newAdPrice)
                .metro(newMetroDto)
                .subCategory(newSubCategoryDto)
                .isActive(true)
                .build();

        adController.updateAd(updatedAdDto);
        AdDto foundUpdatedAdDto = adController.getAdById(updatedAdDto.getId());

        assertEquals(foundNewAdDto.getId(), foundUpdatedAdDto.getId());
        assertEquals(newAdPrice, foundUpdatedAdDto.getPrice());
        assertEquals(newAdName, foundUpdatedAdDto.getName());
        assertEquals(newMetroDto.getId(), foundUpdatedAdDto.getMetro().getId());
        assertEquals(newMetroDto.getName(), foundUpdatedAdDto.getMetro().getName());
        assertEquals(newSubCategoryDto.getId(), foundUpdatedAdDto.getSubCategory().getId());
        assertEquals(newSubCategoryDto.getName(), foundUpdatedAdDto.getSubCategory().getName());

        assertEquals(adPrice, foundNewAdDto.getPrice());
        assertEquals(adName, foundNewAdDto.getName());
        assertEquals(metroDto.getId(), foundNewAdDto.getMetro().getId());
        assertEquals(metroDto.getName(), foundNewAdDto.getMetro().getName());
        assertEquals(subCategoryDto.getId(), foundNewAdDto.getSubCategory().getId());
        assertEquals(subCategoryDto.getName(), foundNewAdDto.getSubCategory().getName());
    }

    @Test
    @Transactional
    public void testCreateAndReceiveByUserId() {
        List<AdDto> adDtoList = createTestAds();

        List<AdDto> adDtoListCreatedByUser = adController.getAdsByUserId(createdUser.getId());
        Set<Long> adDtoIdSet = adDtoList.stream().map(AdDto::getId).collect(Collectors.toSet());

        assertEquals(adDtoList.size(), adDtoListCreatedByUser.size());
        assertTrue(adDtoListCreatedByUser.stream().allMatch(adDto -> adDtoIdSet.contains(adDto.getId())));
    }

    @Test
    @Transactional
    public void testFavouriteList() {
        List<MetroDto> metroDtoList = metroService.getMetros();

        String phoneNumber = "+78005553535";
        String password = "3339";

        UserDto anotherUser = authController.signUp(
                UserDto.builder()
                        .phoneNumber(phoneNumber)
                        .password(password)
                        .build()
        );

        AdDto firstAd = adController.addAd(
                AdDto.builder()
                        .name("Кальмары")
                        .saler(createdUser)
                        .price(300.00)
                        .metro(metroDtoList.get(0))
                        .subCategory(subCategoryService.getSubCategory(1))
                        .isActive(true)
                        .build()
        );

        AdDto secondAd = adController.addAd(
                AdDto.builder()
                        .name("Ножницы")
                        .saler(anotherUser)
                        .price(500.00)
                        .metro(metroDtoList.get(1))
                        .subCategory(subCategoryService.getSubCategory(2))
                        .isActive(true)
                        .build()
        );

        adController.addAd(
                AdDto.builder()
                        .name("Браслет")
                        .saler(anotherUser)
                        .price(800.00)
                        .metro(metroDtoList.get(0))
                        .subCategory(subCategoryService.getSubCategory(3))
                        .isActive(true)
                        .build()
        );

        entityManager.flush();
        entityManager.clear();

        adController.addToFavourites(
                FavouriteDto.builder()
                        .adId(firstAd.getId())
                        .isFavourite(true)
                        .build()
        );

        entityManager.flush();
        entityManager.clear();

        adController.addToFavourites(
                FavouriteDto.builder()
                        .adId(secondAd.getId())
                        .isFavourite(true)
                        .build()
        );

        entityManager.flush();
        entityManager.clear();

        List<AdDto> favoriteAds = adController.getFavoriteAdsByUserId(createdUser.getId());
        Set<Long> favoriteAdIds = favoriteAds.stream().map(AdDto::getId).collect(Collectors.toSet());

        assertEquals(2, favoriteAds.size());
        assertTrue(favoriteAdIds.contains(firstAd.getId()));
        assertTrue(favoriteAdIds.contains(secondAd.getId()));
    }

    private List<AdDto> createTestAds() {
        List<MetroDto> metroDtoList = metroService.getMetros();
        MetroDto metroDto = metroDtoList.stream().findFirst().orElse(null);
        SubCategoryDto subCategoryDto = subCategoryService.getSubCategory(1);

        List<String> adNameList = Stream.of("Машина крутая", "Макароны гречневые", "Наушники беспроводные", "Мышка USB", "Телефон Xiaomi Redmi 10A", "Кружка для кофе", "Зарядка для айфона", "Ложка чайная", "Патч Stone Island", "Бабиджон обычный", "Парик блондинка").collect(Collectors.toList());
        Collections.shuffle(adNameList);
        List<Double> adPriceList = Stream.of(100.00, 300.00, 1000.00, 50000.00, 123.00, 1337.00, 99999.00, 88888.00, 45454.00, 45000.00, 80000.00).collect(Collectors.toList());
        Collections.shuffle(adPriceList);

        List<AdDto> adList = IntStream.range(0, adNameList.size()).boxed().map(index -> AdDto
                .builder()
                .saler(createdUser)
                .name(adNameList.get(index))
                .price(adPriceList.get(index))
                .metro(metroDto)
                .isActive(true)
                .subCategory(subCategoryDto)
                .build()
        ).map(adController::addAd).collect(Collectors.toList());

        entityManager.flush();
        entityManager.clear();
        return adList;
    }
}
