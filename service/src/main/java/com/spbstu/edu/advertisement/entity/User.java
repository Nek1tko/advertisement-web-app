package com.spbstu.edu.advertisement.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "\"user\"")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    
    @Column(columnDefinition = "varchar(100)")
    private String name;
    
    @Column(columnDefinition = "varchar(100)")
    private String surname;
    
    @Column(columnDefinition = "varchar(64)")
    private String password;
    
    @Column(columnDefinition = "numeric(10)")
    private String phone_number;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "favourites", joinColumns = {
            @JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "ad_id")})
    @ToString.Exclude
    private List<Ad> favouriteAds;
    
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Ad> userAds;
}
