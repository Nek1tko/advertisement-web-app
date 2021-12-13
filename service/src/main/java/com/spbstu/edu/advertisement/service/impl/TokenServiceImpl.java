package com.spbstu.edu.advertisement.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spbstu.edu.advertisement.repository.UserRepository;
import com.spbstu.edu.advertisement.vo.SubjectData;
import com.spbstu.edu.advertisement.entity.User;
import com.spbstu.edu.advertisement.service.TokenService;
import com.spbstu.edu.advertisement.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
@Slf4j
public class TokenServiceImpl implements TokenService {
    private final UserRepository userRepository;

    @Override
    public String createToken(String phoneNumber, String password) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user.getPassword().equals(password)) {
            SubjectData subjectData = SubjectData
                    .builder()
                    .userId(user.getId())
                    .phoneNumber(phoneNumber)
                    .creationDateTime(new Date())
                    .build();

            try {
                String convertedSubject = new ObjectMapper().writeValueAsString(subjectData);

                String token = Jwts
                        .builder()
                        .setSubject(convertedSubject)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_LIFETIME))
                        .signWith(SignatureAlgorithm.HS512, KEY)
                        .compact();

                LOGGER.debug("Token was successfully created");
                return token;
            } catch (JsonProcessingException e) {
                LOGGER.error("Conversion error");
                return null;
            }
        } else {
            LOGGER.debug("Token has not created");
            return null;
        }
    }

    @Override
    public SubjectData getSubject(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody();

        try {
            return new ObjectMapper().readValue(claims.getSubject(), SubjectData.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    @Override
    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            LOGGER.error("Invalid JWT token");
        }
        return false;
    }


    private static final Long TOKEN_LIFETIME = 7200000L; // 2 hours
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);
    private static final String KEY = "1231312";
}
