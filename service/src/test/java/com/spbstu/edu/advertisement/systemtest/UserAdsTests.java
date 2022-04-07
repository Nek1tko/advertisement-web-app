package com.spbstu.edu.advertisement.systemtest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.spbstu.edu.advertisement.systemtest.constants.*;
import com.spbstu.edu.advertisement.systemtest.sql.PostgresUtils;
import com.spbstu.edu.advertisement.systemtest.vo.AdVo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UserAdsTests {
    private static Playwright playwright;
    private static Page page;

    @BeforeAll
    static void setUp() {
        playwright = Playwright.create();
        BrowserType chromium = playwright.chromium();
        Browser browser = chromium.launch();
        page = browser.newPage();
        RegistrationUtils.loginUser(page);
    }

    @AfterAll
    static void closePlaywright() {
        playwright.close();
    }

    @BeforeEach
    void cleanDb() {
        PostgresUtils.cleanAdsInDatabase();
        page.click(HeaderSelectors.HOME_SELECTOR);
        page.waitForURL(UrlConstants.MAIN_URL);
    }

    @Test
    void correctUserAdsTest() {
        AdCreationUtils.createAd(page, null);
        page.click(HeaderSelectors.USER_ADS_SELECTOR);
        page.waitForURL(UrlConstants.USER_ADS_URL);
        assertThat(page.locator(AdRecordsTableSelectors.NAME_SELECTOR)).hasText(AdCreationUtils.NAME);
        assertThat(page.locator(AdRecordsTableSelectors.METRO_SELECTOR)).hasText(AdCreationUtils.METRO);
        assertThat(page.locator(AdRecordsTableSelectors.PRICE_SELECTOR)).containsText(AdCreationUtils.PRICE);
    }

    @Test
    void correctUserAdsAfterCreationTest() {
        page.click(HeaderSelectors.CREATE_AD_SELECTOR);
        page.waitForURL(UrlConstants.CREATE_AD_URL);
        page.fill(CreateAdSelectors.NAME_SELECTOR, AdCreationUtils.NAME);
        page.fill(CreateAdSelectors.PRICE_SELECTOR, AdCreationUtils.PRICE);
        page.fill(CreateAdSelectors.DESCRIPTION_SELECTOR, AdCreationUtils.DESCRIPTION);
        page.click(CreateAdSelectors.METRO_SELECTOR);
        page.click("text=" + AdCreationUtils.METRO);
        page.click(CreateAdSelectors.CATEGORY_SELECTOR);
        page.click("text=" + AdCreationUtils.CATEGORY);
        page.click(CreateAdSelectors.SUBCATEGORY_SELECTOR);
        page.click("text=" + AdCreationUtils.SUBCATEGORY);
        page.setInputFiles(CreateAdSelectors.IMGS_SELECTOR, Paths.get(AdCreationUtils.IMG));
        page.click(CreateAdSelectors.CREATE_SELECTOR);

        page.click(HeaderSelectors.USER_ADS_SELECTOR);
        page.waitForURL(UrlConstants.USER_ADS_URL);
        assertThat(page.locator(AdRecordsTableSelectors.NAME_SELECTOR)).hasText(AdCreationUtils.NAME);
        assertThat(page.locator(AdRecordsTableSelectors.METRO_SELECTOR)).hasText(AdCreationUtils.METRO);
        assertThat(page.locator(AdRecordsTableSelectors.PRICE_SELECTOR)).containsText(AdCreationUtils.PRICE);
    }

    @Test
    void invalidAdNameOnCreationTest() {
        AdVo adVo = AdVo
                .builder()
                .name("12")
                .price(AdCreationUtils.PRICE)
                .description(AdCreationUtils.DESCRIPTION)
                .metro(AdCreationUtils.METRO)
                .category(AdCreationUtils.CATEGORY)
                .subCategory(AdCreationUtils.SUBCATEGORY)
                .images(List.of(AdCreationUtils.IMG))
                .build();
        AdCreationUtils.createAd(page, adVo);
        assertThat(page.locator("[role=alert]:above(:text('Название'))"))
                .containsText("Название объявления должно быть длиной от 3 до 50 символов");
    }

    @Test
    void createAdAndWithdrawnFromSaleTest() {
        AdCreationUtils.createAd(page, null);
        page.click(HeaderSelectors.USER_ADS_SELECTOR);
        page.waitForURL(UrlConstants.USER_ADS_URL);
        page.locator( AdRecordsTableSelectors.NAME_SELECTOR + ":has-text('" + AdCreationUtils.NAME + "')").click();
        page.waitForURL(UrlConstants.SELLER_AD_URL);
        page.click(EditAdSelectors.EDIT_SELECTOR);
        page.check(EditAdSelectors.IS_ACTIVE_SELECTOR);
        page.click(CreateAdSelectors.SUBCATEGORY_SELECTOR);
        page.click("text=Гараж");
        page.click(EditAdSelectors.SAVE_SELECTOR);
        page.waitForURL(UrlConstants.SELLER_AD_URL);
        assertThat(page.locator("h4:right-of(:text('" + AdCreationUtils.NAME + "'))"))
                .containsText("снято с продажи");
    }

    @Test
    void createAdAndFindByNameTest() {
        AdCreationUtils.createAd(page, null);
        AdVo adVo = AdVo
                .builder()
                .name("Dummy ad")
                .price("123")
                .description("Dummy description")
                .metro("Дыбенко")
                .category("Транспорт")
                .subCategory("Машина")
                .images(List.of(AdCreationUtils.IMG))
                .build();
        AdCreationUtils.createAd(page, adVo);
        page.click(HeaderSelectors.HOME_SELECTOR);
        page.waitForURL(UrlConstants.MAIN_URL);
        assertThat(page.locator(SearchAdSelector.TABLE_SELECTOR)).hasAttribute("aria-rowcount", String.valueOf(2));
        page.fill(SearchAdSelector.SEARCH_NAME_SELECTOR, AdCreationUtils.NAME.substring(1, 4));
        page.click(SearchAdSelector.SEARCH_SELECTOR);
        assertThat(page.locator(SearchAdSelector.TABLE_SELECTOR)).hasAttribute("aria-rowcount", String.valueOf(1));
        assertThat(page.locator(AdRecordsTableSelectors.NAME_SELECTOR)).containsText(AdCreationUtils.NAME);
    }
}
