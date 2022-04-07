package com.spbstu.edu.advertisement.systemtest;

import com.microsoft.playwright.Page;
import com.spbstu.edu.advertisement.systemtest.constants.CreateAdSelectors;
import com.spbstu.edu.advertisement.systemtest.vo.AdVo;

import java.nio.file.Paths;
import java.util.List;

public class AdCreationUtils {
    public static final String NAME = "Fake name";
    public static final String PRICE = "1001";
    public static final String DESCRIPTION = "Fake description";
    public static final String METRO = "Лесная";
    public static final String CATEGORY = "Недвижимость";
    public static final String SUBCATEGORY = "Квартира";
    public static final String IMG = "src/test/resources/images/placeholder.jpg";
    public static final AdVo DEFAULT_AD = AdVo
            .builder()
            .name(NAME)
            .price(PRICE)
            .description(DESCRIPTION)
            .metro(METRO)
            .category(CATEGORY)
            .subCategory(SUBCATEGORY)
            .images(List.of(IMG))
            .build();

    public static void createAd(Page page, AdVo adVo) {
        AdVo ad = adVo == null ? DEFAULT_AD : adVo;
        page.click("text=Создать объявление");
        page.fill(CreateAdSelectors.NAME_SELECTOR, ad.getName());
        page.fill(CreateAdSelectors.PRICE_SELECTOR, ad.getPrice());
        page.fill(CreateAdSelectors.DESCRIPTION_SELECTOR, ad.getDescription());
        page.click(CreateAdSelectors.METRO_SELECTOR);
        page.click("text=" + ad.getMetro());
        page.click(CreateAdSelectors.CATEGORY_SELECTOR);
        page.click("text=" + ad.getCategory());
        page.click(CreateAdSelectors.SUBCATEGORY_SELECTOR);
        page.click("text=" + ad.getSubCategory());
        ad.getImages().forEach(image -> page.setInputFiles(CreateAdSelectors.IMGS_SELECTOR, Paths.get(image)));
        page.click(CreateAdSelectors.CREATE_SELECTOR);
    }
}
