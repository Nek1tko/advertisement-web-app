package com.spbstu.edu.advertisement.systemtest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.spbstu.edu.advertisement.systemtest.constants.AdRecordsTableSelectors;
import com.spbstu.edu.advertisement.systemtest.constants.HeaderSelectors;
import com.spbstu.edu.advertisement.systemtest.constants.SearchAdSelector;
import com.spbstu.edu.advertisement.systemtest.constants.UrlConstants;
import com.spbstu.edu.advertisement.systemtest.sql.PostgresUtils;
import com.spbstu.edu.advertisement.systemtest.vo.AdVo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchAdsTests {
    private static Playwright playwright;
    private static Page page;
    
    private final static String CORRECT_METRO = "Нарвская";
    private final static String INCORRECT_METRO = "Лесная";
    
    private final static String MIN_PRICE = "1000";
    private final static String MAX_PRICE = "5000";
    
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
    
    private AdVo createAdForMetroFilter(int adPostfix, String metro) {
        return AdVo.builder()
                .name(AdCreationUtils.NAME + adPostfix)
                .price(AdCreationUtils.PRICE)
                .description(AdCreationUtils.DESCRIPTION + adPostfix)
                .metro(metro)
                .category(AdCreationUtils.CATEGORY)
                .subCategory(AdCreationUtils.SUBCATEGORY)
                .images(List.of(AdCreationUtils.IMG))
                .build();
    }
    
    private AdVo createAdForPriceFilter(int adPostfix, String price) {
        return AdVo.builder()
                .name(AdCreationUtils.NAME + adPostfix)
                .price(price)
                .description(AdCreationUtils.DESCRIPTION + adPostfix)
                .metro(AdCreationUtils.METRO)
                .category(AdCreationUtils.CATEGORY)
                .subCategory(AdCreationUtils.SUBCATEGORY)
                .images(List.of(AdCreationUtils.IMG))
                .build();
    }
    
    private AdVo createAdForFilter(String adPostfix) {
        return AdVo.builder()
                .name(AdCreationUtils.NAME + adPostfix)
                .price(AdCreationUtils.PRICE)
                .description(AdCreationUtils.DESCRIPTION + adPostfix)
                .metro(AdCreationUtils.METRO)
                .category(AdCreationUtils.CATEGORY)
                .subCategory(AdCreationUtils.SUBCATEGORY)
                .images(List.of(AdCreationUtils.IMG))
                .build();
    }
    
    @Test
    void filterByMetroStationsTest() {
        IntStream.rangeClosed(1, 6)
                .mapToObj(i -> createAdForMetroFilter(i, i % 2 == 0 ? CORRECT_METRO : INCORRECT_METRO))
                .forEach(ad -> {
                    AdCreationUtils.createAd(page, ad);
                    page.waitForURL(UrlConstants.USER_ADS_URL);
                });
        
        page.click(HeaderSelectors.HOME_SELECTOR);
        page.click(SearchAdSelector.FILTERS_SELECTOR);
        
        page.click(SearchAdSelector.METRO_SELECTOR);
        page.click("li:has-text('" + CORRECT_METRO + "')");
        page.click(SearchAdSelector.APPLY_FILTER_SELECTOR);
        page.click(SearchAdSelector.SEARCH_SELECTOR);
        
        assertThat(page.locator(AdRecordsTableSelectors.METRO_SELECTOR)).hasCount(3);
        List<ElementHandle> elementHandles = page.locator(AdRecordsTableSelectors.NAME_SELECTOR).elementHandles();
        IntStream.rangeClosed(1, 6)
                .filter(i -> i % 2 == 0)
                .forEach(i -> assertEquals(AdCreationUtils.NAME + i, elementHandles.get(3 - i / 2).textContent()));
    }
    
    
    @Test
    void filterByPriceTest() {
        List<String> prices = List.of("1000", "6200", "999", "2500", "100", "5001", "5000");
        IntStream.range(0, 7)
                .mapToObj(i -> createAdForPriceFilter(i, prices.get(i)))
                .forEach(ad -> {
                    AdCreationUtils.createAd(page, ad);
                    page.waitForURL(UrlConstants.USER_ADS_URL);
                });
        
        page.click(HeaderSelectors.HOME_SELECTOR);
        page.click(SearchAdSelector.FILTERS_SELECTOR);
        
        page.fill(SearchAdSelector.MIN_PRICE_SELECTOR, MIN_PRICE);
        page.fill(SearchAdSelector.MAX_PRICE_SELECTOR, MAX_PRICE);
        
        page.click(SearchAdSelector.APPLY_FILTER_SELECTOR);
        page.click(SearchAdSelector.SEARCH_SELECTOR);
        
        assertThat(page.locator(AdRecordsTableSelectors.NAME_SELECTOR)).hasCount(3);
        
        List<ElementHandle> elementHandleNames = page.locator(AdRecordsTableSelectors.NAME_SELECTOR).elementHandles();
        assertEquals(AdCreationUtils.NAME + 0, elementHandleNames.get(2).textContent());
        assertEquals(AdCreationUtils.NAME + 3, elementHandleNames.get(1).textContent());
        assertEquals(AdCreationUtils.NAME + 6, elementHandleNames.get(0).textContent());
        
        List<ElementHandle> elementHandlePrices = page.locator(AdRecordsTableSelectors.PRICE_SELECTOR).elementHandles();
        assertEquals("5000", elementHandlePrices.get(0).textContent().replaceAll("[^0-9]", ""));
        assertEquals("2500", elementHandlePrices.get(1).textContent().replaceAll("[^0-9]", ""));
        assertEquals("1000", elementHandlePrices.get(2).textContent().replaceAll("[^0-9]", ""));
    }
    
    @Test
    void filterByNameTest() {
        List<String> postfixes = List.of("111", "222", "110", "321", "322", "224", "123");
        IntStream.range(0, 7)
                .mapToObj(i -> createAdForFilter(postfixes.get(i)))
                .forEach(ad -> {
                    AdCreationUtils.createAd(page, ad);
                    page.waitForURL(UrlConstants.USER_ADS_URL);
                });
        
        page.click(HeaderSelectors.HOME_SELECTOR);
        
        page.fill(SearchAdSelector.SEARCH_NAME_SELECTOR, AdCreationUtils.NAME);
        page.click(SearchAdSelector.SEARCH_SELECTOR);
        assertThat(page.locator(AdRecordsTableSelectors.NAME_SELECTOR)).hasCount(7);
        
        
        page.fill(SearchAdSelector.SEARCH_NAME_SELECTOR, AdCreationUtils.NAME + "11");
        page.click(SearchAdSelector.SEARCH_SELECTOR);
        assertThat(page.locator(AdRecordsTableSelectors.NAME_SELECTOR)).hasCount(2);
        
        page.fill(SearchAdSelector.SEARCH_NAME_SELECTOR, AdCreationUtils.NAME + "22");
        page.click(SearchAdSelector.SEARCH_SELECTOR);
        assertThat(page.locator(AdRecordsTableSelectors.NAME_SELECTOR)).hasCount(2);
        
        page.fill(SearchAdSelector.SEARCH_NAME_SELECTOR, "22");
        page.click(SearchAdSelector.SEARCH_SELECTOR);
        assertThat(page.locator(AdRecordsTableSelectors.NAME_SELECTOR)).hasCount(3);
        
        
        page.fill(SearchAdSelector.SEARCH_NAME_SELECTOR, AdCreationUtils.NAME + "000");
        page.click(SearchAdSelector.SEARCH_SELECTOR);
        assertThat(page.locator(AdRecordsTableSelectors.NAME_SELECTOR)).hasCount(0);
    }
}
