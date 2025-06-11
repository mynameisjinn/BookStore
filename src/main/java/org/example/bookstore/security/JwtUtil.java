package org.example.bookstore.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret.access}")
    private String accessSecret;

    @Value("${jwt.secret.refresh}")
    private String refreshSecret;

    // 테스트
    @Setter
    @Getter
    private long accessTokenExpirationMs = 1000 * 60 * 30; // 30분

    @Setter
    @Getter
    private long refreshTokenExpirationMs = 1000L * 60 * 60 * 24 * 7; // 7일


    // Key 객체 생성
    private Key getAccessSigningKey() {
        return Keys.hmacShaKeyFor(accessSecret.getBytes());
    }
    private Key getRefreshSigningKey() {
        return Keys.hmacShaKeyFor(refreshSecret.getBytes());
    }

    // JWT 생성
    public String generateAccessToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername()) // 이메일
                .claim("type", "access") // 구분자 추가
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(getAccessSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("type", "refresh") // 구분자 추가
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(getRefreshSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // username(email) 추출
    public String extractUsername(String token, String type) {

        return getClaims(token,type).getBody().getSubject();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token, UserDetails user, String type) {
        String username = extractUsername(token, type);
        return username.equals(user.getUsername()) && !isTokenExpired(token, type);
    }

    // 토큰 만료 여부
    public boolean isTokenExpired(String token, String type) {
        return getClaims(token,type).getBody().getExpiration().before(new Date());
    }

    // 토큰에서 Claims 추출
    private Jws<Claims> getClaims(String token, String type) {
        JwtParser parser;

        if("access".equals(type)) {
            parser = Jwts.parser()
                    .setSigningKey(getAccessSigningKey())
                    .build();
        } else if ("refresh".equals(type)) {
            parser = Jwts.parser()
                    .setSigningKey(getRefreshSigningKey())
                    .build();
        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다");
        }

        return parser.parseClaimsJws(token);
    }

}



