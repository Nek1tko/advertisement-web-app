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

public class LoginTests {
    private static Playwright playwright;
    private static Browser browser;

    private static final String CORRECT_PASSWORD = "Qwerty1!";

    private static final String INCORRECT_PASSWORD = "Qwerty";

    @BeforeAll
    static void setUp() {
        playwright = Playwright.create();
        BrowserType chromium = playwright.chromium();
        browser = chromium.launch();
    }

    @AfterAll
    static void closePlaywright() {
        playwright.close();
    }

    @BeforeEach
    void cleanDb() {
        PostgresUtils.cleanUsersInDatabase();
    }

    @Test
    void correctLoginTest() {
        RegistrationUtils.PASSWORD = CORRECT_PASSWORD;
        Page page = browser.newPage();
        RegistrationUtils.registerUser(page);
        page.waitForURL(UrlConstants.LOGIN_URL);
        page.click(SignUpSelectors.PHONE_SELECTOR);
        page.keyboard().press(KeysConstants.BACKSPACE);
        page.type(SignUpSelectors.PHONE_SELECTOR, RegistrationUtils.PHONE);
        page.fill(SignUpSelectors.PASSWORD_SELECTOR, RegistrationUtils.PASSWORD);
        page.click(SignUpSelectors.LOGIN_SELECTOR);
        assertThat(page).hasURL(UrlConstants.MAIN_URL);
    }

    @Test
    void failedLoginTest() {
        RegistrationUtils.PASSWORD = CORRECT_PASSWORD;
        String unknownPhone = "79999999999";
        String unknownPassword = "Qwerty123";
        Page page = browser.newPage();
        page.navigate(UrlConstants.LOGIN_URL);
        page.click(SignUpSelectors.PHONE_SELECTOR);
        page.keyboard().press(KeysConstants.BACKSPACE);
        page.type(SignUpSelectors.PHONE_SELECTOR, unknownPhone);
        page.fill(SignUpSelectors.PASSWORD_SELECTOR, unknownPassword);
        page.click(SignUpSelectors.LOGIN_SELECTOR);
        assertThat(page.locator(SignUpSelectors.ALERT_SELECTOR))
                .hasText("Такого пользователя не существует или пароль не верный");
    }

    @Test
    void userAlreadyExistsTest() {
        RegistrationUtils.PASSWORD = CORRECT_PASSWORD;
        Page page = browser.newPage();
        RegistrationUtils.registerUser(page);
        page.waitForURL(UrlConstants.LOGIN_URL);
        page.click(SignUpSelectors.REGISTRATION_SELECTOR);
        RegistrationUtils.registerUser(page);
        assertThat(page.locator(SignUpSelectors.ALERT_SELECTOR)).hasText("Ошибка при регистрации!");
    }

    @Test
    void userTryToGoToMainPageWithoutLoginTest() {
        RegistrationUtils.PASSWORD = CORRECT_PASSWORD;
        Page page = browser.newPage();
        page.navigate(UrlConstants.MAIN_URL);
        page.waitForURL(UrlConstants.LOGIN_URL);
        page.click(SignUpSelectors.REGISTRATION_SELECTOR);
        RegistrationUtils.registerUser(page);
        page.waitForURL(UrlConstants.LOGIN_URL);
        page.click(SignUpSelectors.PHONE_SELECTOR);
        page.keyboard().press(KeysConstants.BACKSPACE);
        page.type(SignUpSelectors.PHONE_SELECTOR, RegistrationUtils.PHONE);
        page.fill(SignUpSelectors.PASSWORD_SELECTOR, RegistrationUtils.PASSWORD);
        page.click(SignUpSelectors.LOGIN_SELECTOR);
        assertThat(page).hasURL(UrlConstants.MAIN_URL);
    }

    @Test
    void registrationWithEasyPasswordTest() {
        RegistrationUtils.PASSWORD = INCORRECT_PASSWORD;
        Page page = browser.newPage();
        page.navigate(UrlConstants.LOGIN_URL);
        page.click(SignUpSelectors.REGISTRATION_SELECTOR);
        RegistrationUtils.registerUser(page);
        assertThat(page.locator(SignUpSelectors.ALERT_SELECTOR))
                .hasText("Пароль должен содержать хотя бы одну цифру и спец символ!");
    }
}
