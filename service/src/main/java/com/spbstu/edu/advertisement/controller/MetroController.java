package com.spbstu.edu.advertisement.controller;

import com.spbstu.edu.advertisement.dto.MetroDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/metro")
public class MetroController {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<MetroDto> getMetroStations() {
        return null;
    }
}
