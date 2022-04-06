package com.spbstu.edu.advertisement.systemtest;

import com.microsoft.playwright.Page;
import com.spbstu.edu.advertisement.systemtest.constants.KeysConstants;
import com.spbstu.edu.advertisement.systemtest.constants.CreateAdSelectors;
import com.spbstu.edu.advertisement.systemtest.constants.UrlConstants;
import java.nio.file.Paths;

public class AdCreationUtils {
    public static final String NAME = "Fake name";
    public static final String PRICE = "1001";
    public static final String DESCRIPTION = "Fake description";
    public static final String METRO = "Лесная";
    public static final String CATEGORY = "Недвижимость";
    public static final String SUBCATEGORY = "Квартира";
    public static final String IMG = "src/test/resources/images/placeholder.jpg";

    public static void createAd(Page page) {
        page.click("text=Создать объявление");
        page.fill(CreateAdSelectors.NAME_SELECTOR, NAME);
        page.fill(CreateAdSelectors.PRICE_SELECTOR, PRICE);
        page.fill(CreateAdSelectors.DESCRIPTION_SELECTOR, DESCRIPTION);
        page.click(CreateAdSelectors.METRO_SELECTOR);
        page.click("text=" + METRO);
        page.click(CreateAdSelectors.CATEGORY_SELECTOR);
        page.click("text=" + CATEGORY);
        page.click(CreateAdSelectors.SUBCATEGORY_SELECTOR);
        page.click("text=" + SUBCATEGORY);
        page.setInputFiles(CreateAdSelectors.IMGS_SELECTOR, Paths.get(IMG));
        page.click(CreateAdSelectors.CREATE_SELECTOR);
    }
}
