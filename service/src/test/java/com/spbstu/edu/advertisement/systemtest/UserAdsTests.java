package com.spbstu.edu.advertisement.systemtest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.spbstu.edu.advertisement.systemtest.constants.AdRecordsTableSelectors;
import com.spbstu.edu.advertisement.systemtest.constants.CreateAdSelectors;
import com.spbstu.edu.advertisement.systemtest.constants.HeaderSelectors;
import com.spbstu.edu.advertisement.systemtest.constants.UrlConstants;
import com.spbstu.edu.advertisement.systemtest.constants.UserAdsSelectors;
import com.spbstu.edu.advertisement.systemtest.sql.PostgresUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

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
    }

    @Test
    void correctUserAdsTest() {
        AdCreationUtils.createAd(page);
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
}
