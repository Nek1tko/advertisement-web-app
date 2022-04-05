package com.spbstu.edu.advertisement.systemtest;

import com.microsoft.playwright.Page;
import com.spbstu.edu.advertisement.systemtest.constants.KeysConstants;
import com.spbstu.edu.advertisement.systemtest.constants.CreateAdSelectors;
import com.spbstu.edu.advertisement.systemtest.constants.UrlConstants;
import java.nio.file.Path;

public class AdCreationUtils {
    public static final String NAME = "Fake name";
    public static final String PRICE = "1001";
    public static final String DESCRIPTION = "Fake description";
    public static final String METRO = "Лесная";
    public static final String CATEGORY = "Недвижимость";
    public static final String SUBCATEGORY = "Квартира";
    public static final String IMG = "src/test/resources/images/placeholder.jpg";

    public static void createAd(Page page) {
        page.navigate(UrlConstants.CREATE_AD_URL);
        page.fill(CreateAdSelectors.NAME_SELECTOR, NAME);
        page.fill(CreateAdSelectors.PRICE_SELECTOR, PRICE);
        page.fill(CreateAdSelectors.DESCRIPTION_SELECTOR, DESCRIPTION);
        page.selectOption(CreateAdSelectors.METRO_SELECTOR, METRO);
        page.selectOption(CreateAdSelectors.CATEGORY_SELECTOR, CATEGORY);
        page.selectOption(CreateAdSelectors.SUBCATEGORY_SELECTOR, SUBCATEGORY);
        page.setInputFiles(CreateAdSelectors.IMGS_SELECTOR, Path.of(IMG));
        page.click(CreateAdSelectors.CREATE_SELECTOR);
    }
}
