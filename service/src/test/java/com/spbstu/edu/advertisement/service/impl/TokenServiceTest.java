package com.spbstu.edu.advertisement.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spbstu.edu.advertisement.entity.User;
import com.spbstu.edu.advertisement.exception.CustomException;
import com.spbstu.edu.advertisement.exception.ExceptionId;
import com.spbstu.edu.advertisement.repository.UserRepository;
import com.spbstu.edu.advertisement.vo.SubjectData;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.impl.DefaultJwtParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {
    @Mock
    private UserRepository userRepository;

    @Spy
    private DefaultJwtBuilder jwtBuilder;

    @Spy
    private DefaultJwtParser jwtParser;

    @InjectMocks
    private TokenServiceImpl tokenService;

    private final MessageDigestPasswordEncoder messageDigest =  new MessageDigestPasswordEncoder("SHA-256");

    private final String phoneNumber = "+78005553535";
    private final String encodedPassword = messageDigest.encode("123");
    private final String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJ1c2VySWRcIjoxMjMsXCJwaG9uZU51bWJlclwiOlwiKzc4MDA1NTUzNTM1XCIsXCJjcmVhdGlvbkRhdGVUaW1lXCI6MTY0NzgxMjA2MjAxMn0iLCJpYXQiOjE2NDc4MTIwNjIsImV4cCI6OTIyMzM3MjAzNjg1NDc3NX0.ERqwQit4iWxgT7MgaCuWN4jdIGvDyGhX8sXthFBoyIXR4hHN2TD2P-ElneWwYSxaQ-CH6t_dj135MiKZX3MjoA";
    private final String key = Base64.getEncoder().encodeToString("333333".getBytes(StandardCharsets.UTF_8));

    private User user;


    @BeforeEach
    public void setUp() throws Exception {
        user = new User();
        user.setId(123L);
        user.setName("Nikita");
        user.setSurname("Krasnoperov");
        user.setPassword(encodedPassword);
        user.setPhoneNumber(phoneNumber);
        setKey();
    }

    private void setKey() throws Exception {
        Field keyField = TokenServiceImpl.class.getDeclaredField("KEY");
        keyField.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(keyField, keyField.getModifiers() & ~Modifier.FINAL);
        keyField.set(null, key);
    }

    @Test
    public void testCreateToken() throws JsonProcessingException {
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Date> issuedDateCaptor = ArgumentCaptor.forClass(Date.class);
        ArgumentCaptor<Date> expirationDateCaptor = ArgumentCaptor.forClass(Date.class);

        MockedStatic<Jwts> jwts = mockStatic(Jwts.class);
        jwts.when(Jwts::builder).thenReturn(jwtBuilder);

        doReturn(user).when(userRepository).findByPhoneNumber(phoneNumber);
        doCallRealMethod().when(jwtBuilder).setSubject(subjectCaptor.capture());
        doCallRealMethod().when(jwtBuilder).setIssuedAt(issuedDateCaptor.capture());
        doCallRealMethod().when(jwtBuilder).setExpiration(expirationDateCaptor.capture());
        doCallRealMethod().when(jwtBuilder).signWith(any(SignatureAlgorithm.class), eq(key));

        String jwt = tokenService.createToken(phoneNumber, encodedPassword);
        String[] jwtParts = jwt.split("\\.");

        SubjectData capturedSubject = new ObjectMapper().readValue(subjectCaptor.getValue(), SubjectData.class);
        Date capturedIssuedDate = issuedDateCaptor.getValue();
        Date capturedExpirationDate = expirationDateCaptor.getValue();

        assertEquals(3, jwtParts.length);
        assertNotNull(capturedIssuedDate);
        assertNotNull(capturedExpirationDate);
        assertEquals(-1, capturedIssuedDate.compareTo(capturedExpirationDate));
        assertEquals(phoneNumber, capturedSubject.getPhoneNumber());
        assertEquals(user.getId(), capturedSubject.getUserId());

        jwts.close();
    }

    @Test
    public void testUserNotFound() {
        doReturn(null).when(userRepository).findByPhoneNumber(phoneNumber);

        CustomException thrownException = assertThrows(
                CustomException.class,
                () -> tokenService.createToken(phoneNumber, encodedPassword)
        );

        verify(userRepository).findByPhoneNumber(phoneNumber);
        verifyNoMoreInteractions(userRepository);

        assertEquals(ExceptionId.USER_NOT_FOUND, thrownException.getId());
    }

    @Test
    public void testIncorrectPassword() {
        String invalidEncodedPassword = messageDigest.encode("1234");
        doReturn(user).when(userRepository).findByPhoneNumber(phoneNumber);

        CustomException thrownException = assertThrows(
                CustomException.class,
                () -> tokenService.createToken(phoneNumber, invalidEncodedPassword)
        );

        verify(userRepository).findByPhoneNumber(phoneNumber);
        verifyNoMoreInteractions(userRepository);

        assertEquals(ExceptionId.INVALID_AUTHENTICATION, thrownException.getId());
    }

    @Test
    public void testGetSubject() throws JsonProcessingException {
        ArgumentCaptor<String> tokenCaptor = ArgumentCaptor.forClass(String.class);

        MockedStatic<Jwts> jwts = mockStatic(Jwts.class);
        jwts.when(Jwts::parser).thenReturn(jwtParser);

        doCallRealMethod().when(jwtParser).parseClaimsJws(tokenCaptor.capture());

        SubjectData subject = tokenService.getSubject(token);

        String capturedToken = tokenCaptor.getValue();
        verify(jwtParser).parseClaimsJws(capturedToken);
        verify(jwtParser).setSigningKey(key);

        assertEquals(token, capturedToken);
        assertNotNull(subject);
        assertEquals(phoneNumber, subject.getPhoneNumber());
        assertEquals(user.getId(), subject.getUserId());

        jwts.close();
    }

    @Test
    public void testGetExpirationDate() {
        ArgumentCaptor<String> tokenCaptor = ArgumentCaptor.forClass(String.class);

        MockedStatic<Jwts> jwts = mockStatic(Jwts.class);
        jwts.when(Jwts::parser).thenReturn(jwtParser);

        doCallRealMethod().when(jwtParser).parseClaimsJws(tokenCaptor.capture());

        Date expirationDate = tokenService.getExpirationDate(token);

        String capturedToken = tokenCaptor.getValue();
        verify(jwtParser).parseClaimsJws(capturedToken);
        verify(jwtParser).setSigningKey(key);

        assertEquals(token, capturedToken);
        assertNotNull(expirationDate);

        jwts.close();
    }

    @Test
    public void testSuccessfulValidation() {
        ArgumentCaptor<String> tokenCaptor = ArgumentCaptor.forClass(String.class);

        MockedStatic<Jwts> jwts = mockStatic(Jwts.class);
        jwts.when(Jwts::parser).thenReturn(jwtParser);

        doCallRealMethod().when(jwtParser).parseClaimsJws(tokenCaptor.capture());

        boolean validationResult = tokenService.validate(token);

        String capturedToken = tokenCaptor.getValue();
        verify(jwtParser).parseClaimsJws(capturedToken);
        verify(jwtParser).setSigningKey(key);

        assertEquals(token, capturedToken);
        assertTrue(validationResult);
        jwts.close();
    }

    @Test
    public void testFailedValidation() {
        final String invalidToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJ1c2VySWRcIjoxMjMsXCJwaG9uZU51bWJlclwiOlwiKz";

        ArgumentCaptor<String> tokenCaptor = ArgumentCaptor.forClass(String.class);

        MockedStatic<Jwts> jwts = mockStatic(Jwts.class);
        jwts.when(Jwts::parser).thenReturn(jwtParser);

        doCallRealMethod().when(jwtParser).parseClaimsJws(tokenCaptor.capture());

        boolean validationResult = tokenService.validate(invalidToken);

        String capturedToken = tokenCaptor.getValue();
        verify(jwtParser).parseClaimsJws(capturedToken);
        verify(jwtParser).setSigningKey(key);

        assertEquals(invalidToken, capturedToken);
        assertFalse(validationResult);
        jwts.close();
    }
}
