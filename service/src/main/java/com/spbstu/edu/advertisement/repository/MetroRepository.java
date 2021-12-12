package com.spbstu.edu.advertisement.repository;

import com.spbstu.edu.advertisement.entity.Metro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetroRepository extends JpaRepository<Metro, Long> {

}
