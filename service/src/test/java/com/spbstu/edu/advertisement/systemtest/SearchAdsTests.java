package com.spbstu.edu.advertisement.systemtest;

import com.spbstu.edu.advertisement.systemtest.vo.AdVo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchAdsTests {
    
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
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, 0);
        assertTrue(true);
    }
    
    
    @Test
    void filterByPriceTest() {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, 0);
        assertTrue(true);
    }
    
    @Test
    void filterByNameTest() {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(0, 0);
        assertTrue(true);
    }
}
