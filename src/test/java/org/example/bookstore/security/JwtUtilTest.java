package org.example.bookstore.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    private UserDetails testUser;

    @BeforeEach
    void setUp() {
        // 1분 만료 시간 설정
        jwtUtil.setAccessTokenExpirationMs(1000 * 60);

        testUser = User.withUsername("test@example.com")
                .password("password")
                .roles("USER")
                .build();
    }

    @Test
    void testGenerateAndValidateToken() {
        String token = jwtUtil.generateAccessToken(testUser);

        assertNotNull(token);
        assertEquals("test@example.com", jwtUtil.extractUsername(token));
        assertTrue(jwtUtil.validateToken(token, testUser));
    }

    @Test
    void testTokenExpirationAfter1Minute() throws InterruptedException {
        jwtUtil.setAccessTokenExpirationMs(1000); // 1초 만료
        String token = jwtUtil.generateAccessToken(testUser);

        Thread.sleep(1500); // 1.5초 기다림

        // 만료됐으니 isTokenExpired에서 예외 발생 가능
        boolean isExpired;
        try {
            isExpired = jwtUtil.isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            isExpired = true;
        }

        assertTrue(isExpired);

        // validateToken도 예외 발생 가능
        boolean isValid;
        try {
            isValid = jwtUtil.validateToken(token, testUser);
        } catch (ExpiredJwtException e) {
            isValid = false;
        }

        assertFalse(isValid);
    }

}