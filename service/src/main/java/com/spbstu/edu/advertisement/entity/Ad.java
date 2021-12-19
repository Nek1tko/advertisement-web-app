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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Ad {
    public static final String PRICE_PATH = "price";
    public static final String METRO_PATH = "metro";
    public static final String SUB_CATEGORY_PATH = "subCategory";

    @Id
    @SequenceGenerator(
            name = "ad_sequence",
            sequenceName = "ad_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_sequence")
    private Long id;
    
    @Column(columnDefinition = "varchar(100)")
    private String name;
    
    @Column(columnDefinition = "text")
    private String description;
    
    @Column(columnDefinition = "decimal")
    private Double price;
    
    @ManyToOne
    @JoinColumn(name = "metro_id")
    private Metro metro;
    
    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User saler;
    
    @Column(name = "creation_date", columnDefinition = "DATE")
    private LocalDate creationDate;
    
    @Column(name = "is_active", columnDefinition = "boolean")
    private Boolean isActive;
    
    @ManyToMany(mappedBy = "favouriteAds")
    @ToString.Exclude
    private List<User> users;
    
    @OneToMany(mappedBy = "ad")
    @ToString.Exclude
    private List<Image> images;
}
