package com.spbstu.edu.advertisement.systemtest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.spbstu.edu.advertisement.systemtest.constants.AdRecordsTableSelectors;
import com.spbstu.edu.advertisement.systemtest.constants.AdSelectors;
import com.spbstu.edu.advertisement.systemtest.constants.HeaderSelectors;
import com.spbstu.edu.advertisement.systemtest.constants.UrlConstants;
import com.spbstu.edu.advertisement.systemtest.sql.PostgresUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FavoritesTests {
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
    void addToFavoritesTest() {
        AdCreationUtils.createAd(page, null);
        page.click(HeaderSelectors.HOME_SELECTOR);
        page.waitForURL(UrlConstants.MAIN_URL);
        page.click(AdRecordsTableSelectors.NAME_SELECTOR);
        page.click(AdSelectors.FAVORITE_SELECTOR);
        page.click(HeaderSelectors.PROFILE_SELECTOR);
        page.click(HeaderSelectors.FAVORITES_SELECTOR);
        
        assertThat(page.locator(AdRecordsTableSelectors.NAME_SELECTOR)).hasText(AdCreationUtils.NAME);
        assertThat(page.locator(AdRecordsTableSelectors.METRO_SELECTOR)).hasText(AdCreationUtils.METRO);
        assertThat(page.locator(AdRecordsTableSelectors.PRICE_SELECTOR)).containsText(AdCreationUtils.PRICE);
    }

    @Test
    void removeFromFavoritesTest() {
        AdCreationUtils.createAd(page, null);
        page.click(HeaderSelectors.HOME_SELECTOR);
        page.waitForURL(UrlConstants.MAIN_URL);
        page.click(AdRecordsTableSelectors.NAME_SELECTOR);
        page.click(AdSelectors.FAVORITE_SELECTOR);
        page.click(HeaderSelectors.PROFILE_SELECTOR);
        page.click(HeaderSelectors.FAVORITES_SELECTOR);

        page.click(AdRecordsTableSelectors.NAME_SELECTOR);
        page.click(AdSelectors.FAVORITE_SELECTOR);
        page.click(HeaderSelectors.PROFILE_SELECTOR);
        page.click(HeaderSelectors.FAVORITES_SELECTOR);

        assertThat(page.locator(".MuiDataGrid-root")).containsText("No rows");
    }
}
