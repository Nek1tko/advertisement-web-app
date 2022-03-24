package com.spbstu.edu.advertisement.controller;


import com.spbstu.edu.advertisement.dto.MetroDto;
import com.spbstu.edu.advertisement.service.MetroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static com.spbstu.edu.advertisement.service.impl.ImageServiceImpl.UPLOAD_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = "classpath:sql/init.sql")
public class MetroControllerTest {
    @Autowired
    private MetroController metroController;
    
    @Test
    @Transactional
    public void getMetroFromDBTest() {
        List<MetroDto> metroDtoList = metroController.getMetroStations();
        assertEquals(3, metroDtoList.size());
        assertEquals("Девяткино", metroDtoList.get(0).getName());
        assertEquals("Гражданский проспект", metroDtoList.get(1).getName());
        assertEquals("Академическая", metroDtoList.get(2).getName());
    }
}
