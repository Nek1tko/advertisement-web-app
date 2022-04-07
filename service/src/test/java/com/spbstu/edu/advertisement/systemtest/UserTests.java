package com.spbstu.edu.advertisement.systemtest;

import com.microsoft.playwright.*;
import com.spbstu.edu.advertisement.systemtest.constants.EditUserSelectors;
import com.spbstu.edu.advertisement.systemtest.constants.HeaderSelectors;
import com.spbstu.edu.advertisement.systemtest.constants.UrlConstants;
import com.spbstu.edu.advertisement.systemtest.sql.PostgresUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests {
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
    void testEditUser() {
        String newName = "Andrey";
        String newSurname = "Oguzkov";

        page.click(HeaderSelectors.PROFILE_SELECTOR);
        page.click(HeaderSelectors.PROFILE_DATA_SELECTOR);
        page.waitForURL(UrlConstants.PERSONAL_AREA_URL);
        page.click(EditUserSelectors.OPEN_SELECTOR);
        page.fill(EditUserSelectors.NAME_SELECTOR, newName);
        page.fill(EditUserSelectors.SURNAME_SELECTOR, newSurname);
        page.click(EditUserSelectors.SAVE_SELECTOR);

        page.click(HeaderSelectors.PROFILE_SELECTOR);
        page.click(HeaderSelectors.PROFILE_DATA_SELECTOR);
        page.waitForURL(UrlConstants.PERSONAL_AREA_URL);
        List<ElementHandle> initials = page.querySelectorAll("h4:below(:text('Главная'))");
        assertEquals(2, initials.size());
        assertEquals(newName, initials.get(0).textContent());
        assertEquals(newSurname, initials.get(1).textContent());
    }
}
