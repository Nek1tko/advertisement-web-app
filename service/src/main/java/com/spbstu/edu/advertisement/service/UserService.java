package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.entity.User;

import java.util.List;

public interface UserService {
    List<Ad> listAds(long userId);
    
    List<Ad> listFavouriteAds(long userId);
    
    User getUser(long userId);
    
    User addUser(User user);
    
    User updateUser(User user);
    
    void deleteUser(long userId);
}
