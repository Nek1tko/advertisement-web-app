package com.spbstu.edu.advertisement.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Metro {
    @Id
    @SequenceGenerator(
            name = "metro_sequence",
            sequenceName = "metro_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metro_sequence")
    private Long id;
    
    @Column(columnDefinition = "varchar(100)")
    private String name;
    
    @OneToMany(mappedBy = "metro")
    @ToString.Exclude
    private List<Ad> ads;
}