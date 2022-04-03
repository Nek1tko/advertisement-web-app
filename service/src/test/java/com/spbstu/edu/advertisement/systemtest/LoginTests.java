package com.spbstu.edu.advertisement.systemtest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTests {
    private static Playwright playwright;
    private static Browser browser;

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

    @Test
    void failedLoginTest() {
        String phone = "79090433333";
        String password = "Qwerty123";
        Page page = browser.newPage();
        page.navigate("http://localhost:3000/login");
        page.type("input[name='phone']", phone);
        page.fill("text=Пароль", password);
        page.click("id=loginButton");
        assertThat(page.locator(".MuiAlert-message:visible"))
                .hasText("Такого пользователя не существует или пароль не верный");
    }
}
