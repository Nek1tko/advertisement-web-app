package com.spbstu.edu.advertisement.systemtest;

import com.microsoft.playwright.Page;
import com.spbstu.edu.advertisement.systemtest.constants.KeysConstants;
import com.spbstu.edu.advertisement.systemtest.constants.SignUpSelectors;
import com.spbstu.edu.advertisement.systemtest.constants.UrlConstants;

public class RegistrationUtils {
    public static final String PHONE = "79090433333";
    public static final String NAME = "FakeName";
    public static final String SURNAME = "FakeSurname";
    public static String PASSWORD = "Qwerty1!";

    public static void registerUser(Page page) {
        page.navigate(UrlConstants.SIGN_UP_URL);
        page.fill(SignUpSelectors.NAME_SELECTOR, NAME);
        page.fill(SignUpSelectors.SURNAME_SELECTOR, SURNAME);
        page.click(SignUpSelectors.PHONE_SELECTOR);
        page.keyboard().press(KeysConstants.BACKSPACE);
        page.type(SignUpSelectors.PHONE_SELECTOR, PHONE);
        page.fill(SignUpSelectors.PASSWORD_SELECTOR, PASSWORD);
        page.fill(SignUpSelectors.PASSWORD_CONFIRMATION_SELECTOR, PASSWORD);
        page.click(SignUpSelectors.REGISTRATION_SELECTOR);
    }
}
