package com.krist.util;

import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {

    @Value("${secret-key}")
    private String secretKey;

    @Value("${token-expiration}")
    private Integer expiration;

    public SecretKey getSignInKey() {
        // Reference: https://github.com/jwtk/jjwt?tab=readme-ov-file#secretkey-formats
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        return key;
    }

    public String createToken(Map<String, Object> extraClaims, String subject) {
        // Official Docs Reference:
        // https://github.com/jwtk/jjwt?tab=readme-ov-file#creating-a-jws
        // Example:
        // https://github.com/koushikkothagal/spring-security-jwt/blob/master/src/main/java/io/javabrains/springsecurityjwt/util/JwtUtil.java
        String jwt = Jwts.builder().claims(extraClaims).subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), Jwts.SIG.HS256).compact();
        return jwt;
    }

    public Claims parseToken(String token) throws JwtException {
        // Reference: https://github.com/jwtk/jjwt?tab=readme-ov-file#reading-a-jws
        Jws<Claims> jws = Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token);
        return jws.getPayload();
    }
}
