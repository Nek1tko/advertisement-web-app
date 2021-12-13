package com.spbstu.edu.advertisement.service;

import com.spbstu.edu.advertisement.vo.SubjectData;

import java.util.Date;

public interface TokenService {
    String createToken(String phoneNumber, String password);

    SubjectData getSubject(String token);

    Date getExpirationDate(String token);

    boolean validate(String token);
}
