package com.spbstu.edu.advertisement.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spbstu.edu.advertisement.entity.User;
import com.spbstu.edu.advertisement.exception.InvalidPasswordException;
import com.spbstu.edu.advertisement.exception.UserNotFoundException;
import com.spbstu.edu.advertisement.repository.UserRepository;
import com.spbstu.edu.advertisement.service.TokenService;
import com.spbstu.edu.advertisement.vo.SubjectData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
@Slf4j
public class TokenServiceImpl implements TokenService {
    private static final Long TOKEN_LIFETIME = 7200000L; // 2 hours
    private static final String KEY = "1231312";
    
    private final UserRepository userRepository;
    
    @Override
    public String createToken(String phoneNumber, String password) throws JsonProcessingException {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (user.getPassword().equals(password)) {
            SubjectData subjectData = SubjectData
                    .builder()
                    .userId(user.getId())
                    .phoneNumber(phoneNumber)
                    .creationDateTime(new Date())
                    .build();
            
            String convertedSubject = new ObjectMapper().writeValueAsString(subjectData);
            
            return Jwts
                    .builder()
                    .setSubject(convertedSubject)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + TOKEN_LIFETIME))
                    .signWith(SignatureAlgorithm.HS512, KEY)
                    .compact();
        } else {
            throw new InvalidPasswordException();
        }
    }
    
    @Override
    public SubjectData getSubject(String token) throws JsonProcessingException {
        Claims claims = getClaims(token);
        return new ObjectMapper().readValue(claims.getSubject(), SubjectData.class);
    }
    
    @Override
    public Date getExpirationDate(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration();
    }
    
    @Override
    public boolean validate(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
