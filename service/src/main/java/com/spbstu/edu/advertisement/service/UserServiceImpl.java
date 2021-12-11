package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.entity.User;
import com.spbstu.edu.advertisement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public List<Ad> listAds(long userId) {
        return getUser(userId).getUserAds();
    }
    
    @Override
    public List<Ad> listFavouriteAds(long userId) {
        return getUser(userId).getFavouriteAds();
    }
    
    @Override
    public User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }
    
    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    @Override
    public void deleteUser(long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException exception) {
            throw new RuntimeException("There is no user with this ID");
        }
    }
}
