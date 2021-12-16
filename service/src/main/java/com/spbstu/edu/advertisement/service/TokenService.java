package com.spbstu.edu.advertisement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spbstu.edu.advertisement.vo.SubjectData;

import java.util.Date;

public interface TokenService {
    String createToken(String phoneNumber, String password) throws JsonProcessingException;

    SubjectData getSubject(String token) throws JsonProcessingException;

    Date getExpirationDate(String token);

    boolean validate(String token);
}
