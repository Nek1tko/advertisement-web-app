package com.spbstu.edu.advertisement.security;

import com.spbstu.edu.advertisement.entity.User;
import com.spbstu.edu.advertisement.repository.UserRepository;
import com.spbstu.edu.advertisement.service.TokenService;
import com.spbstu.edu.advertisement.vo.SubjectData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtTokenFilterTest {
    @Mock
    private  UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private JwtTokenFilter jwtTokenFilter;

    @Spy
    private FilterChain filterChain = new MockFilterChain();

    private MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
    private HttpServletResponse httpServletResponse = new MockHttpServletResponse();

    private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJ1c2VySWRcIjoxMjMsXCJwaG9uZU51bWJlclwiOlwiKzc4MDA1NTUzNTM1XCIsXCJjcmVhdGlvbkRhdGVUaW1lXCI6MTY0NzQ2OTM2ODIwOH0iLCJpYXQiOjE2NDc0NjkzNjgsImV4cCI6MTY0NzQ3NjU2OH0.WKg2zVvHL9aYxxAbQff4VEmu_U9gAhI-C8xqfLyehdX25DTKH2RZFV1-Ez_xK_3lNww1EZEWlvfwPbRh31JlIw";

    @Test
    public void testRequestWithNoHeader() throws ServletException, IOException {
        ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        ArgumentCaptor<HttpServletResponse> responseCaptor = ArgumentCaptor.forClass(HttpServletResponse.class);

        doCallRealMethod().when(filterChain).doFilter(requestCaptor.capture(), responseCaptor.capture());

        jwtTokenFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        HttpServletRequest capturedRequest = requestCaptor.getValue();
        HttpServletResponse capturedResponse = responseCaptor.getValue();

        verify(filterChain).doFilter(capturedRequest, capturedResponse);

        verifyNoMoreInteractions(filterChain, userRepository, tokenService);
    }

    @Test
    public void testRequestWithValidToken() throws ServletException, IOException {
        String phoneNumber = "+78005553535";
        Long userId = 12345L;

        SubjectData subject = new SubjectData();
        subject.setPhoneNumber(phoneNumber);
        subject.setUserId(userId);

        User user = new User();
        user.setId(userId);
        user.setName("Nikita");
        user.setSurname("Krasnoperov");
        user.setPhoneNumber(phoneNumber);

        httpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        ArgumentCaptor<HttpServletResponse> responseCaptor = ArgumentCaptor.forClass(HttpServletResponse.class);

        doCallRealMethod().when(filterChain).doFilter(requestCaptor.capture(), responseCaptor.capture());
        doReturn(true).when(tokenService).validate(token);
        doReturn(subject).when(tokenService).getSubject(token);
        doReturn(user).when(userRepository).findByPhoneNumber(phoneNumber);

        jwtTokenFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        HttpServletRequest capturedRequest = requestCaptor.getValue();
        HttpServletResponse capturedResponse = responseCaptor.getValue();

        verify(filterChain).doFilter(capturedRequest, capturedResponse);
        verify(tokenService).validate(token);
        verify(tokenService).getSubject(token);
        verify(userRepository).findByPhoneNumber(phoneNumber);

        verifyNoMoreInteractions(filterChain, userRepository, tokenService);
    }

    @Test
    public void testRequestWithInvalidToken() throws ServletException, IOException {
        httpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        ArgumentCaptor<HttpServletRequest> requestCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        ArgumentCaptor<HttpServletResponse> responseCaptor = ArgumentCaptor.forClass(HttpServletResponse.class);

        doCallRealMethod().when(filterChain).doFilter(requestCaptor.capture(), responseCaptor.capture());
        doReturn(false).when(tokenService).validate(token);

        jwtTokenFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        HttpServletRequest capturedRequest = requestCaptor.getValue();
        HttpServletResponse capturedResponse = responseCaptor.getValue();

        verify(filterChain).doFilter(capturedRequest, capturedResponse);
        verify(tokenService).validate(token);

        verifyNoMoreInteractions(filterChain, userRepository, tokenService);
    }
}
