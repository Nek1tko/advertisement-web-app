package com.spbstu.edu.advertisement.systemtest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.spbstu.edu.advertisement.systemtest.constants.KeysConstants;
import com.spbstu.edu.advertisement.systemtest.constants.SignUpSelectors;
import com.spbstu.edu.advertisement.systemtest.constants.UrlConstants;
import com.spbstu.edu.advertisement.systemtest.sql.PostgresUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UserAdsTests {
    private static Playwright playwright;
    private static Browser browser;
    private static Page page;

    private static final String CORRECT_PASSWORD = "Qwerty1!";

    @BeforeAll
    static void setUp() {
        playwright = Playwright.create();
        BrowserType chromium = playwright.chromium();
        browser = chromium.launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(false));
        RegistrationUtils.PASSWORD = CORRECT_PASSWORD;
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
    void correctUserAdsAfterCreationTest() {
        AdCreationUtils.createAd(page);
        page.click("text=Мои объявления");
    }
}
