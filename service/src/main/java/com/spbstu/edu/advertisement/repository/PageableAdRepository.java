package com.spbstu.edu.advertisement.repository;

import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.vo.PageableContext;

import java.util.List;

public interface PageableAdRepository {
    List<Ad> findAdsByFilters(PageableContext pageableContext);

    Long countAdsByFilter(PageableContext pageableContext);
}
