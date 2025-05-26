package org.example.bookstore.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    // 테스트
    @Setter
    private long accessTokenExpirationMs = 1000 * 60 * 30; // 30분

    @Setter
    private long refreshTokenExpirationMs = 1000L * 60 * 60 * 24 * 7; // 7일


    // Key 객체 생성
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // JWT 생성
    public String generateAccessToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername()) // 이메일
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // username(email) 추출
    public String extractUsername(String token) {
        return getClaims(token).getBody().getSubject();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token, UserDetails user) {
        String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    // 토큰 만료 여부
    public boolean isTokenExpired(String token) {
        return getClaims(token).getBody().getExpiration().before(new Date());
    }

    // 토큰에서 Claims 추출
    private Jws<Claims> getClaims(String token) {
        JwtParser parser = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build();
        return parser.parseClaimsJws(token);
    }

}



